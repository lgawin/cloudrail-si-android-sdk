# CloudRail - Integrate Mulitple Services With Just One API

<p align="center">
  <img src="http://cloudrail.com/wp-content/uploads/2016/05/cloudrail_SI_github.png"/>
</p>

CloudRail is a free software library which abstracts multiple APIs from different providers into a single and universal interface.

Full documentation can be found at https://docs.cloudrail.com/

With CloudRail, you can easily integrate external APIs into your application. CloudRail is an abstracted interface that takes several services and then gives a developer-friendly API that uses common functions between all providers. This means that, for example, upload() works in exactly the same way for Dropbox as it does for Google Drive, OneDrive, and other Cloud Storage Services.

## Code Sample
```` java
// CloudStorage cs = new Box(context, "[clientIdentifier]", "[clientSecret]");
// CloudStorage cs = new OneDrive(context, "[clientIdentifier]", "[clientSecret]");
// CloudStorage cs = new GoogleDrive(context, "[clientIdentifier]", "[clientSecret]");
CloudStorage cs = new Dropbox(context, "[clientIdentifier]", "[clientSecret]");
new Thread() {
    @Override
    public void run() {
        cs.createFolder("/TestFolder"); // <---
        InputStream stream = null;
        try {
            AssetManager assetManager = getAssets();
            stream = assetManager.open("UserData.csv");
            long size = assetManager.openFd("UserData.csv").getLength();
            cs.upload("/TestFolder/Data.csv", stream, size); // <---
        } catch (Exception e) {
            // TODO: handle error
        } finally {
            // TODO: close stream
        }
    }
}.start();
````

## Current Services

* Dropbox
* Box
* Google Drive
* OneDrive

## Advantages of Using CloudRail

* Consistent Interfaces: As functions work the same across all services, you can perform tasks between services simply.

* Easy Authentication: CloudRail includes easy ways to authentication, to remove one of the biggest hassles of coding for external APIs.

* Switch services instantly: One line of code is needed to set up the service you are using. Changing which service is as simple as changing the name to the one you wish to use.

* Simple Documentation: There is no searching around Stack Overflow for the answer. The CloudRail documentation at https://docs.cloudrail.com/ is regularly updated, clean, and simple to use. 

* No Maintenance Times: The CloudRail Libraries are updated when a provider changes their API.

## Get Updates

To keep updated with CloudRail, including any new providers that are added, just add your email address to https://cloudrail.com/updates/.

## Pricing

CloudRail is free to use. For all projects; commercial and non-commercial. We want APIs to be accessible to all developers. We want APIs to be easier to manage. This is only possible if there are free, powerful tools out there to do this.

