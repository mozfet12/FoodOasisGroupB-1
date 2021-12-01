# Food Oasis

**Food Oasis** is a proposed Android app that aims to alleviate the effect of food deserts on the dietary health of people in low-income areas. It uses the Google Places API to search for grocery stores and farmers' markets in the vicinity of a given location. It will filter these results by cost. In addition, to make sure it does not return convenience stores and fast food restaurants,the app will verify location results with *United States Department of Agriculture(USDA)* databases of grocers and farmer's markets. Additional proposed features include being able to save favorite locations and notifications with links to weekly grocery ads for these locations.

## How Food Oasis works?
The app takes either the current location of the user or it takes the address/zipcode entered by the user along with the distance in miles to be travelled. With the help of that it uses Google Map API to convert the geolocation into the latitude and longitude. These entities are passed to the USDA database API of farmers' markets to identify the nearest markets and return them into the app. The app marks those location on Google Map and and also shows a list of the same. User can add the places into its Favourite location to check on daily basis and also to get notified with newer offers/updates(future aspect). By clicking on the direction button user can get the direction from his/her location to the specified farmer's market.

## What Food Oasis requires?
1. Android mobile device
2. Internet connectivity
3. GPS services turn on on the device

## How to use Food Oasis?
1. Download the application from [HERE](https://github.com/nplimbani/FoodOasisGroupB/blob/main/Food%20Oasis.apk)
2. Install the apk file on your android mobile device (As it is for the demonstrating purpose only it will ask you to install apk from third party, so allow that in setting)
3. App is using location to suggest you the best and nearest option you need to allow the location services when asking
4. There will be two fields for address and radius(the miles you want to travel from the specified location)
5. You can add the data in these fields or you can use you current location also by the given specified button
6. There is a floating button for next after locating the stores so you can see all in one list and add them in the **Favourite** list
7. You can also **get the direction** of specified farmer's market from your current location
8. There is a floating button for Favourite section where you can access you saved locations



> **_NOTE:_**  The project is using several open sources libraries and dependencies for the development purpose named, Google Maps Services, USDA Database API, Play services, Gson, RecyclerView.
