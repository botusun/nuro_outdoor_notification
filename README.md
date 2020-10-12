# nuro_outdoor_notification

As the pandemic of Corona Virus, more people around the world is working remotely, and for Japan, the most trust-worthy ISP is Nuro by Sony.

Because Nuro is utilising the dark fibre from NTT, and two-time construction take a really long time waiting especially the outdoor construction.

This piece of software is designed to auto-check if a construction date is available prior to the current one.

Auto-check is fired every five minutes, and it will send push notification through Telegram or IFTTT push notification.

Just modify the config in the nuro_outdoor_notification/src/main/resources/application.properties and it should okay to go.

```
// Access key to nuro construciton website, it should be the very last part of the URL in SMS
nuroKey =
// If you set this part, a push notification will be sent through IFTTT
iftttUri = 
// Set if you want Telegram notification
telegramBotToken =
telegramChatId = 
// How many week to check, by default is 4 weeks ahead only
checkWeeks = 4


// Ignore this part
spring.main.log-startup-info=false
logging.level.org.springframework = WARN
```
