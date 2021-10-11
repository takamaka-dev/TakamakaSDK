# Takamaka Android Sdk Guide

https://cdn.takamaka.dev/androidsdkdocs/javadocSdk.html

Takamaka SDK for Android Applications (AiliA SA)
Takamaka is proud to present Android SDK prototype, easy to understand the functionalities and demonstrate the correct work flow.

# Start up actions!
Launch the application a clean app will appear.
 
 <p align="center">
	<img src="https://downloads.takamaka.dev/FILES/AndroidSdk/fig11_fix.jpeg" />
</p>

Once clicked the rounded button with T letter a vertical line of action buttons will appear.

 <p align="center">
	<img src="https://downloads.takamaka.dev/FILES/AndroidSdk/fig22.jpeg" />
</p>

## Wallet login button.
If there is a wallet previously created with the app. 

 - Click Login button a new page will come out.
 - Insert username and password. 
 - Press login button.



<p align="center">
	<img src="https://downloads.takamaka.dev/FILES/AndroidSdk/fig112.jpeg" />
</p>
The home screen will appear.

## Create wallet button.
- Simply by writing the name of the wallet and 
- Insert password.
- Repeat Password
- Click Create button
<p align="center">
	<img src="https://downloads.takamaka.dev/FILES/AndroidSdk/fig44.jpeg" />
</p>

Less than 3 seconds and our app will generate a brand-new wallet transiting to the home screen of this wallet. 



## Restore wallet.
- Select a new name or the same one used previously for the wallet being restored.
- Insert password.
- Repeat Password
- Insert the list of the keywords in the correct order.
- Click Restore button

<p align="center">
	<img src="https://downloads.takamaka.dev/FILES/AndroidSdk/fig55.jpeg" />
</p>

Less than 3 seconds and our app will restore our wallet transiting to the home screen of this wallet.


 


# Home Screen button 
The home screen for this app is presented with the following features 

- Identicon image.
- The respective balance for this address. TKG, TKR, FTKG, FTKR.
- Current address field that can be copied.
- Cypher Wallet algorithm used.
- Name of the wallet given when created.
- Refresh index button whose duty is to generate a new address once invoked and the field besides inserting a number that will identify our new address.

<p align="center">
	<img src="https://downloads.takamaka.dev/FILES/AndroidSdk/fig66.jpeg" />
</p>

# Send Token
- Identicon From Address image.
	- From Address field
- Identicon To Address image.
	- To Address field 	
- TKG Field to edit with value to include in the transaction.
- TKR Field to edit with value to include in the transaction.
- Message Field, personalized message to include in the transaction.
- Verify button, responsible to inform the user of the cost of the transaction that is going to pay.
- Send Button, is in charge to validate and send the transaction to the blockchain.

<p align="center">
	<img src="https://downloads.takamaka.dev/FILES/AndroidSdk/fig77_fix2.jpeg" />
</p>


## Transaction Info-Status
- . Status
Once the transaction is sent through this new screen is presented informing the user of the status. 

<p align="center">
	<img src="https://downloads.takamaka.dev/FILES/AndroidSdk/fig99_fix.jpeg" />
</p>

If the transaction is succesful or not.

- New Transaction button takes us to the Send Token section if a new transaction is needed to be created.
- Go home button, takes us to the wallet home screen.

# Explorer section
Explorer is an integreated web page directly pointing to the transactions pool that can be consultable using web filters. 
<p align="center">
	<img src="https://downloads.takamaka.dev/FILES/AndroidSdk/fig88_fix.jpeg" />
</p>
Setting the filters is easy to spot up the transaction of interst and consult the body of transaction.

# Settings section
This button takes us to the area of the application used to change environment, from production to test area and viceversa everytime it is needed for develompent purposes.

<p align="center">
	<img src="https://downloads.takamaka.dev/FILES/AndroidSdk/new_settings_sdk_android.png" />
</p>
Simply activate the environment of interest by activating the radio button.

It is also possible to display your 25 secret words by clicking on the button "SHOW 25 WORDS"

<p align="center">
	<img src="https://downloads.takamaka.dev/FILES/AndroidSdk/new_settings_2_sdk_android.png" />
</p>

# Logout Button

<p align="center">
	<img src="https://downloads.takamaka.dev/FILES/AndroidSdk/fig110.jpeg" />
</p>
Just click on the button to logout from your Wallet Session

## In order to switch from production to test enviroment for oauth procedure just use the following curl

curl 'https://testsite.takamaka.org/oauth/authorize?response_type=code&client_id=dev&redirect_uri=https%3A%2F%2Ftestsite.takamaka.org%3A20443%2Foauth%2Fauthorized&scope=email+address' \
  -H 'Connection: keep-alive' \
  -H 'Pragma: no-cache' \
  -H 'Cache-Control: no-cache' \
  -H 'sec-ch-ua: " Not A;Brand";v="99", "Chromium";v="90", "Google Chrome";v="90"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'Upgrade-Insecure-Requests: 1' \
  -H 'Origin: https://testsite.takamaka.org' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.72 Safari/537.36' \
  -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9' \
  -H 'Sec-Fetch-Site: same-origin' \
  -H 'Sec-Fetch-Mode: navigate' \
  -H 'Sec-Fetch-User: ?1' \
  -H 'Sec-Fetch-Dest: document' \
  -H 'Referer: https://testsite.takamaka.org/oauth/authorize?response_type=code&client_id=dev&redirect_uri=https%3A%2F%2Ftestsite.takamaka.org%3A20443%2Foauth%2Fauthorized&scope=email+address' \
  -H 'Accept-Language: it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7' \
  -H 'Cookie: session=eyJkZXZfb2F1dGhyZWRpciI6Imh0dHBzOi8vdGVzdHNpdGUudGFrYW1ha2Eub3JnOjIwNDQzL29hdXRoL2F1dGhvcml6ZWQifQ.YJqzQw.hhKyH1pVrafRwnIdzK0RucMvkZQ' \
  -H 'dnt: 1' \
  -H 'sec-gpc: 1' \
  --data-raw 'confirm=yes&email=EMAIL&password=PASSWORD' \
  --compressed

You can convert the code in a runnable java version by using Postman

# API used to work with the application 
## Exact search FROM:
The folowing curl code can be used to establish on searching bases the from key field.
```bash
curl --location --request GET 'https://takamaka.io/api/search/from/l9Bk_09EFVo7VFS0pLS1d5cs8I4rnHhG_iTGRoWXH_s.'
```
