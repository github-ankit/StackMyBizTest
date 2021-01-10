# StackMyBizTest

This application provides User login/register via google's account. Here we have two screens : 
1. Login Screen -  It has two fields UserName and Password with a login button and a Forgot Password link.
2. User's Profile screen - It has a ImaveView that displays user's image and couple of TextBox to display user's name, email etc.

This application also stores users's login and logout events into Firebase database.

Here, Google Sigin and adding the login event to firebase database also have been added.

## Requiremenets 

1. We need Android studio to run this project.
2. A Physical Device to run the application
3. For google login we an email id

## Features

1. It provides user login using google account
2. Data of the user saved in database 

## Flow

This app uses a google account to login user, 

1. Click on SignIn with Google Button and you will be able to signin by choosing your account.
2. There are two feilds username(emailid) and password , both the feilds are validated on clicking login button.
3. Then we a Profile section where name, email ID and picture of the user is displayed.
4. On Profile screen we have a logout buttons and clicking on it will be loggedOut and returned to the login screen.


-------------

Answer to the question : If you were given more time to improve your solution what would you improve and why?

- Since app UI is the first thing everyone notices, I would have used motion Layout for animating the textfields of the login page so that app doesn't look simple.

I would also love to write the code in Kotlin, since it new to me and others are adopting it i also like to code in kotlin to get more command over that language.






