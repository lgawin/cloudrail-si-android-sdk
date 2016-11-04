# Advanced Authentication Sample

This sample should give you a good starting point for implementing the advanced authentication method. The main difference to the standard authentication is the fact that we now open the website, where the users needs to login to their account, in the users standard browser. By doing this, the user can verify that the website that was opened actually belongs to the provider he selected. The WebView in the standard authentication does not show any details about which site was opened or if the certificate belongs to the provider. However, this does not make the standard authentication less secure than the advanced one, it is a matter of trust. Since the WebView is started from our SDK, the website that is opened can not be modified by a third party. So if you trust our SDK to not open any malicious website and your users trust your app not to do so, there is no need to switch to the advanced method.

## How to enable it

In order for the SDK to know that you want to use the advanced method you need to set the following flag:

```java
CloudRail.setAdvancedAuthenticationMode(true);
```

This can for instance be done right after you set your license key. By adding this line, whenever a login is necessary, the SDK will open the users browser and display the login page. When the user correctly enters his credentials, he will be redirected to the standard redirect URL: https://www.cloudrailauth.com/auth which will result in a 'page not found' error. To avoid this, we have to declare, that the app wants to handle this URL by adding an intent filter to the manifest file:

```xml
<intent-filter>
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <data android:host="www.cloudrailauth.com" android:scheme="https" android:path="/auth" />
</intent-filter>
```

This will give the user the choice to open the website with your app. When the user does so, you need to pass the intent that the browser sent to your activity to our SDK like this:

```java
CloudRail.setAuthenticationResponse(getIntent());
```

After doing so, you just trigger the login process again and the SDK will continue the same way as if it would return from the WebView.

There are some things you need to keep in mind when using this method. For instance, when the browser redirects to your app, the app is restarted so you need to make sure that you save any state you need before triggering the authentication process. A way this could be done is shown in this sample, so go ahead and check it out.
