import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useCart } from '../context/CartContext'
import { useAuth } from '../context/AuthContext'
import axios from 'axios'
import { Trash2, Plus, Minus } from 'lucide-react'

const Cart = () => {
  const { cart, removeFromCart, updateQuantity, getTotalPrice, clearCart } = useCart()
  const { user } = useAuth()
  const navigate = useNavigate()
  const [loading, setLoading] = useState(false)

  const handleCheckout = async () => {
    if (!user) {
      navigate('/login')
      return
    }

    if (cart.length === 0) {
      return
    }

    setLoading(true)
    try {
      // Group items by restaurant
      const restaurantGroups = cart.reduce((groups, item) => {
        const restaurantId = item.restaurantId
        if (!groups[restaurantId]) {
          groups[restaurantId] = {
            restaurantId,
            restaurantName: item.restaurantName,
            items: [],
            totalPrice: 0
          }
        }
        groups[restaurantId].items.push(item)
        groups[restaurantId].totalPrice += item.price * item.quantity
        return groups
      }, {})

      // Create orders for each restaurant
      const orderPromises = Object.values(restaurantGroups).map(group => 
        axios.post('/api/orders', {
          userId: user.id,
          restaurantId: group.restaurantId,
          totalPrice: group.totalPrice
        })
      )

      await Promise.all(orderPromises)
      clearCart()
      navigate('/profile')
    } catch (error) {
      console.error('Error placing order:', error)
    } finally {
      setLoading(false)
    }
  }

  if (cart.length === 0) {
    return (
      <div className="max-w-4xl mx-auto">
        <h1 className="text-3xl font-bold text-gray-800 mb-8">Shopping Cart</h1>
        <div className="bg-white rounded-lg shadow-md p-8 text-center">
          <p className="text-gray-600 text-lg mb-4">Your cart is empty</p>
          <button
            onClick={() => navigate('/')}
            className="bg-primary-600 text-white px-6 py-2 rounded-lg hover:bg-primary-700 transition-colors"
          >
            Browse Restaurants
          </button>
        </div>
      </div>
    )
  }

  return (
    <div className="max-w-4xl mx-auto">
      <h1 className="text-3xl font-bold text-gray-800 mb-8">Shopping Cart</h1>
      
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Cart Items */}
        <div className="lg:col-span-2">
          <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-xl font-semibold mb-4">Cart Items</h2>
            
            <div className="space-y-4">
              {cart.map(item => (
                <div key={item.id} className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
                  <div className="flex-1">
                    <h3 className="font-semibold text-gray-800">{item.name}</h3>
                    <p className="text-sm text-gray-600">{item.restaurantName}</p>
                    <p className="text-primary-600 font-semibold">${item.price}</p>
                  </div>
                  
                  <div className="flex items-center space-x-2">
                    <button
                      onClick={() => updateQuantity(item.id, item.quantity - 1)}
                      className="bg-gray-200 text-gray-700 rounded-full p-1 hover:bg-gray-300 transition-colors"
                    >
                      <Minus size={16} />
                    </button>
                    
                    <span className="text-lg font-semibold min-w-[30px] text-center">
                      {item.quantity}
                    </span>
                    
                    <button
                      onClick={() => updateQuantity(item.id, item.quantity + 1)}
                      className="bg-primary-600 text-white rounded-full p-1 hover:bg-primary-700 transition-colors"
                    >
                      <Plus size={16} />
                    </button>
                    
                    <button
                      onClick={() => removeFromCart(item.id)}
                      className="bg-red-500 text-white rounded-full p-1 hover:bg-red-600 transition-colors ml-2"
                    >
                      <Trash2 size={16} />
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Order Summary */}
        <div className="lg:col-span-1">
          <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-xl font-semibold mb-4">Order Summary</h2>
            
            <div className="space-y-3 mb-6">
              {cart.map(item => (
                <div key={item.id} className="flex justify-between text-sm">
                  <span>{item.name} x {item.quantity}</span>
                  <span>${(item.price * item.quantity).toFixed(2)}</span>
                </div>
              ))}
              
              <hr className="my-3" />
              
              <div className="flex justify-between font-semibold text-lg">
                <span>Total</span>
                <span>${getTotalPrice().toFixed(2)}</span>
              </div>
            </div>
            
            <button
              onClick={handleCheckout}
              disabled={loading || !user}
              className="w-full bg-primary-600 text-white py-3 px-4 rounded-lg hover:bg-primary-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {loading ? 'Processing...' : user ? 'Place Order' : 'Login to Order'}
            </button>
            
            {!user && (
              <p className="text-sm text-gray-600 mt-2 text-center">
                Please login to place your order
              </p>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}

export default Cart
