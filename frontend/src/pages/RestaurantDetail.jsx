import { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import MenuItemCard from '../components/MenuItemCard'
import { Star, MapPin, Clock } from 'lucide-react'
import { useAuth } from '../context/AuthContext'

const RestaurantDetail = () => {
  const { id } = useParams()
  const { user } = useAuth()
  const [restaurant, setRestaurant] = useState(null)
  const [menuItems, setMenuItems] = useState([])
  const [reviews, setReviews] = useState([])
  const [loading, setLoading] = useState(true)
  const [newReview, setNewReview] = useState({ rating: 5, comment: '' })

  useEffect(() => {
    fetchRestaurantData()
  }, [id])

  const fetchRestaurantData = async () => {
    try {
      const [restaurantRes, menuRes, reviewsRes] = await Promise.all([
        axios.get(`/api/restaurants/${id}`),
        axios.get(`/api/menu-items/restaurant/${id}`),
        axios.get(`/api/reviews/restaurant/${id}`)
      ])
      
      setRestaurant(restaurantRes.data)
      setMenuItems(menuRes.data)
      setReviews(reviewsRes.data)
    } catch (error) {
      console.error('Error fetching restaurant data:', error)
    } finally {
      setLoading(false)
    }
  }

  const handleSubmitReview = async (e) => {
    e.preventDefault()
    if (!user) return

    try {
      await axios.post('/api/reviews', {
        userId: user.id,
        restaurantId: parseInt(id),
        rating: newReview.rating,
        comment: newReview.comment
      })
      
      // Refresh reviews
      const response = await axios.get(`/api/reviews/restaurant/${id}`)
      setReviews(response.data)
      
      // Reset form
      setNewReview({ rating: 5, comment: '' })
    } catch (error) {
      console.error('Error submitting review:', error)
    }
  }

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="text-xl text-gray-600">Loading restaurant details...</div>
      </div>
    )
  }

  if (!restaurant) {
    return (
      <div className="text-center py-12">
        <p className="text-gray-600 text-lg">Restaurant not found.</p>
      </div>
    )
  }

  return (
    <div className="max-w-6xl mx-auto">
      {/* Restaurant Header */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-8">
        <div className="flex flex-col md:flex-row gap-6">
          <div className="md:w-1/3">
            <img
              src={restaurant.imageUrl}
              alt={restaurant.name}
              className="w-full h-64 object-cover rounded-lg"
              onError={(e) => {
                e.target.src = 'https://via.placeholder.com/400x300?text=Restaurant'
              }}
            />
          </div>
          
          <div className="md:w-2/3">
            <h1 className="text-3xl font-bold text-gray-800 mb-4">
              {restaurant.name}
            </h1>
            
            <div className="flex items-center text-gray-600 mb-2">
              <MapPin size={20} className="mr-2" />
              <span>{restaurant.address}</span>
            </div>
            
            <div className="flex items-center text-gray-600 mb-4">
              <Star size={20} className="mr-2 text-yellow-500 fill-current" />
              <span className="font-semibold">{restaurant.rating}</span>
              <span className="ml-2 text-gray-500">({reviews.length} reviews)</span>
            </div>
            
            <div className="flex items-center text-gray-600 mb-4">
              <Clock size={20} className="mr-2" />
              <span className="capitalize">{restaurant.cuisine} Cuisine</span>
            </div>
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Menu Items */}
        <div className="lg:col-span-2">
          <h2 className="text-2xl font-bold text-gray-800 mb-6">Menu</h2>
          
          {menuItems.length === 0 ? (
            <p className="text-gray-600">No menu items available.</p>
          ) : (
            <div className="space-y-4">
              {menuItems.map(item => (
                <MenuItemCard key={item.id} item={item} />
              ))}
            </div>
          )}
        </div>

        {/* Reviews */}
        <div>
          <h2 className="text-2xl font-bold text-gray-800 mb-6">Reviews</h2>
          
          {/* Add Review Form */}
          {user && (
            <div className="bg-white rounded-lg shadow-md p-4 mb-6">
              <h3 className="text-lg font-semibold mb-4">Write a Review</h3>
              <form onSubmit={handleSubmitReview}>
                <div className="mb-4">
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Rating
                  </label>
                  <select
                    value={newReview.rating}
                    onChange={(e) => setNewReview({ ...newReview, rating: parseInt(e.target.value) })}
                    className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary-500"
                  >
                    {[5, 4, 3, 2, 1].map(rating => (
                      <option key={rating} value={rating}>
                        {rating} Star{rating !== 1 ? 's' : ''}
                      </option>
                    ))}
                  </select>
                </div>
                
                <div className="mb-4">
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Comment
                  </label>
                  <textarea
                    value={newReview.comment}
                    onChange={(e) => setNewReview({ ...newReview, comment: e.target.value })}
                    rows="3"
                    className="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary-500"
                    placeholder="Share your experience..."
                    required
                  />
                </div>
                
                <button
                  type="submit"
                  className="w-full bg-primary-600 text-white py-2 px-4 rounded-lg hover:bg-primary-700 transition-colors"
                >
                  Submit Review
                </button>
              </form>
            </div>
          )}

          {/* Reviews List */}
          <div className="space-y-4">
            {reviews.length === 0 ? (
              <p className="text-gray-600">No reviews yet.</p>
            ) : (
              reviews.map(review => (
                <div key={review.id} className="bg-white rounded-lg shadow-md p-4">
                  <div className="flex items-center justify-between mb-2">
                    <span className="font-semibold text-gray-800">
                      {review.userName}
                    </span>
                    <div className="flex items-center">
                      <Star size={16} className="text-yellow-500 fill-current mr-1" />
                      <span className="text-sm">{review.rating}</span>
                    </div>
                  </div>
                  <p className="text-gray-600 text-sm">{review.comment}</p>
                  <p className="text-gray-400 text-xs mt-2">
                    {new Date(review.timestamp).toLocaleDateString()}
                  </p>
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  )
}

export default RestaurantDetail
