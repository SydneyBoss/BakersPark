import { Link } from 'react-router-dom'
import { Star, MapPin } from 'lucide-react'

const RestaurantCard = ({ restaurant }) => {
  return (
    <Link to={`/restaurant/${restaurant.id}`} className="block">
      <div className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300">
        <div className="relative h-48">
          <img
            src={restaurant.imageUrl}
            alt={restaurant.name}
            className="w-full h-full object-cover"
            onError={(e) => {
              e.target.src = 'https://via.placeholder.com/400x300?text=Restaurant'
            }}
          />
          <div className="absolute top-2 right-2 bg-white px-2 py-1 rounded-full flex items-center space-x-1">
            <Star size={16} className="text-yellow-500 fill-current" />
            <span className="text-sm font-semibold">{restaurant.rating}</span>
          </div>
        </div>
        
        <div className="p-4">
          <h3 className="text-lg font-semibold text-gray-800 mb-2">
            {restaurant.name}
          </h3>
          
          <div className="flex items-center text-gray-600 mb-2">
            <MapPin size={16} className="mr-1" />
            <span className="text-sm">{restaurant.address}</span>
          </div>
          
          <div className="flex items-center justify-between">
            <span className="text-sm text-gray-500 capitalize">
              {restaurant.cuisine}
            </span>
          </div>
        </div>
      </div>
    </Link>
  )
}

export default RestaurantCard
