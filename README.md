# Cins Widget Software

We have developed Server/Client manner CinsWidget software.

Server automatically gathers the data from the given links. Server contains musics that clients can download.

## Server is responsible from:
1 - Gathering weather information of Manisa from https://www.mgm.gov.tr/?il=manisa [5 minute refreshment]

2 - Gathering currency information (USD, EUR) from https://www.tcmb.gov.tr/wps/wcm/connect/tr/tcmb+tr/main+page+site+area/bugun [5 minute refreshment]

3 - Gathering News Headlines (max.5 news) from https://www.trtworld.com [5 minute refreshment]

4 - Admin's choice: 5 music mp3 file [15 minutes] (Directory listing is refreshed every 15 minute to see whether the new mp3 files exist or not.)


## Client is responsible for the following items:
1 - Gathers weather information from Server and shows on CinsWidget window at the top.

2 - Takes the currency input(i.e. USD)and converts to other currency (i.e.TL) based on Server's current info. 

3 - Show the current news taken from Server.

4 - Download 5 music files (current playlist) from Server.
