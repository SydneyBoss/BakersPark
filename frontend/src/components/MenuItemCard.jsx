import { useCart } from '../context/CartContext'
import { Plus, Minus } from 'lucide-react'

const MenuItemCard = ({ item }) => {
  const { addToCart, removeFromCart, cart } = useCart()
  
  const cartItem = cart.find(cartItem => cartItem.id === item.id)
  const quantity = cartItem ? cartItem.quantity : 0

  return (
    <div className="bg-white rounded-lg shadow-md p-4">
      <div className="flex justify-between items-start mb-3">
        <div className="flex-1">
          <h3 className="text-lg font-semibold text-gray-800 mb-1">
            {item.name}
          </h3>
          <p className="text-gray-600 text-sm mb-2">
            {item.description}
          </p>
          <p className="text-lg font-bold text-primary-600">
            ${item.price}
          </p>
        </div>
        
        <div className="flex items-center space-x-2">
          {quantity > 0 && (
            <>
              <button
                onClick={() => removeFromCart(item.id)}
                className="bg-red-500 text-white rounded-full p-1 hover:bg-red-600 transition-colors"
              >
                <Minus size={16} />
              </button>
              <span className="text-lg font-semibold min-w-[20px] text-center">
                {quantity}
              </span>
            </>
          )}
          <button
            onClick={() => addToCart(item)}
            className="bg-primary-600 text-white rounded-full p-1 hover:bg-primary-700 transition-colors"
          >
            <Plus size={16} />
          </button>
        </div>
      </div>
    </div>
  )
}

export default MenuItemCard
