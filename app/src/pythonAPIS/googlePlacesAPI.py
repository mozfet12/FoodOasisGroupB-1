# pip install googlemaps
# pip install prettyprint
# https://www.youtube.com/watch?v=qkSmuquMueA&t=24s was used in the creation of this file

import googlemaps
import pprint
import time
import requests

#API_KEY = 'INSERTAPIKEYHERE'
API_KEY = 'AIzaSyAgmp-JgcYmE73aC3Y2qZtpcIPYyFM-KkM'
#Define our Client
gmaps = googlemaps.Client(key= API_KEY)

#Geocoding
#Tutorial followed: https://www.youtube.com/watch?v=d1QGLwie9YU

#Enter Address
address = input('Please enter address in the following format: "StreetNo Street, City, State": ')
milesTraveled = int(input('How many miles do you want to travel?: '))
convertMilestoMeters = milesTraveled*1609


params = {
    'key': API_KEY,
    'address': address
}

base_url = 'https://maps.googleapis.com/maps/api/geocode/json?'
response = requests.get(base_url, params=params).json()
response.keys()

if response['status'] == 'OK':
    geometry = response['results'][0]['geometry']
    lat = geometry['location']['lat']
    lon = geometry['location']['lng']

concatLatLon = str(lat) + ", " + str(lon);


# Define our Search
# Documentation: https://developers.google.com/maps/documentation/javascript/places#place_search_requests
places_result = gmaps.places_nearby(location= concatLatLon, radius = convertMilestoMeters, open_now = False, rank_by = "prominence", type = 'supermarket')

#pprint.pprint(places_result)

for place in places_result['results']:
    #define my place id
    my_place_id = place['place_id']

    #define the fields we want sent back to us
    my_fields = ['name', 'formatted_phone_number', 'type']

    # make a request for the details
    place_details = gmaps.place(place_id = my_place_id, fields = my_fields)
    
    #print the results
    print(place_details)
