# NagiosAlert

An Android application developed as a class project at Simon Bolivar University (Venezuela). 
This app allow you to monitor the status of the clients of your Nagios server and their services.

Authors
--------
Carlos Aponte & Aileen Moreno


Contents
---------

NagiosAlert-App -> contains the app code.

NagiosAlert-Server -> contains the code of the servlets that act as the app server. This servlets use the **status.dat** 
file of your Nagios server and answer the different http request based on that information. Therefore, the servlts need
to have access to an up-to-date version of the file or even the original file.

Screenshots
-----------

Splash screen

![](https://github.com/caat91/NagiosAlert/blob/master/NagiosAlert-App/samples/nagiosAlert_splash.png)

List of hosts

![](https://github.com/caat91/NagiosAlert/blob/master/NagiosAlert-App/samples/nagiosAlert_hostList.png)

Details of the service status

![](https://github.com/caat91/NagiosAlert/blob/master/NagiosAlert-App/samples/nagiosAlert_serviceDetails.png)


