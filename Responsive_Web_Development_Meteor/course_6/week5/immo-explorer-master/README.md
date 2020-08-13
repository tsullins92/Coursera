immo-explorer app compares a property price to previously sold properties in the area, in order to help price a property you own, or check the property you're about to buy is not over-priced. It's a single page app

To run the app, do the following:
```
meteor npm install --save @babel/runtime
meteor run
```

```
*** 1st section (search form):

The form contains the following fields:
    Address (text area,mandatory): fill with any adress that can be understood by openstreetmap
    Surface area (text area, in square meters)
    # rooms (text area)
    Type (menu): house,flat
    Compare (submit button)
The user fills the fields, then press Compare button.
If fields are correct (address is mandatory, surface area is a positive float or integer, # rooms is a positive integer), the app jumps to the 2nd section (the map)
Otherwise, the system signals the erros and stays on the search form.

*** 2nd section (property map):

The map is centered on the address entered.
The map also shows a number of properties from the database, with the same characteristics as the property entered by the user (same type, same surface area (+-15%), same number of rooms), at their respective address.
The map displays the price of the properties next to them.
When hovering on a property, a table will be displayed with its properties.
Initial zooming: the zoom level is computed so that the map displays at least 3 properties.
There are a few controls on the map:
    Type (checkboxes): X house X flat
    Surface area (slider with min-max, from 0 to 500sqmeters)
    # rooms (slider with min-max, from 0 to 10)
By default, the controls values are inherited from the search form.
If not defined by the user, the optional parameters are unset (min = max = 0)
As the user moves the controls, the properties are re-computed in real-time, showing only the matching properties

Limitations (demonstrator): the demonstrator works for Paris only, because the database with all France would have been really huge

Bugs:
- no input controls in the search fields, client-side (on server side, any inputs < 0 for surface or # rooms is ignored)
- map controls are not implemented yet
- accounts not implemented yet (no possibility to create a list of searches per user)
- probably many others

Here are some addresses in Paris (in french, of course :-) ):
champs-elysées,paris
6 rue pernelle, paris
1, rue dauphine, paris




```

