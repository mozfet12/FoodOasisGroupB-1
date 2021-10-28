# pip install googlemaps
# pip install prettyprint
# https://www.nylas.com/blog/use-python-requests-module-rest-apis/ was used in the creation of this file

import googlemaps
import pprint
import time
import requests

API_KEY = 'AIzaSyAgmp-JgcYmE73aC3Y2qZtpcIPYyFM-KkM'
#Define our Client
gmaps = googlemaps.Client(key= API_KEY)

address = input('Please enter address in the following format: "StreetNo Street, City, State": ')
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



response = requests.get("http://search.ams.usda.gov/farmersmarkets/v1/data.svc/locSearch?lat=" + str(lat) + "&lng=" + str(lon))
pprint.pprint(response.json())
