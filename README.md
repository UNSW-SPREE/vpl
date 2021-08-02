# Virtual Production Line

## Installing and Running the VPL

### docker (Any Platform)
---

First install docker for your platform - https://docs.docker.com/get-docker/

Then run the following command to start the application using docker:

`docker run -p 8080:8080 ghcr.io/unsw-spree/vpl:release`

Point your browser to http://localhost:8080

## Building

### on a windows machine
---

1. Install Java SDK
2. Install Maven
3. Run `mvn clean package` to build the deployment package

### vscode remote containers
---
Developing inside docker containers ensures that all the dependencies required to build the application are all available and are the correct version. These dependencies are all inside the container so they don't pollute the developers machine with multiple versions of sdks. It does however make it a little more tricky to debug a gui application like this (See below)

1. Clone the repository 
2. Use VSCode Remote containers method to open the project - https://code.visualstudio.com/docs/remote/containers
3. Start debugging in vscode (See notes on remote x11 below)

#### (Notes on remote x11)

Docker has no gui so if you want to run the application inside the container you need to forward the x11 port to your local machine and run a local x11 server that will do the display. Instructions for Mac are available at: https://medium.com/@mreichelt/how-to-show-x11-windows-within-docker-on-mac-50759f4b65cb

Basically once you have XQuartz installed and change the settings to allow network connections, all you need to do is run xhost + 127.0.0.1 on your Mac prior to debugging


