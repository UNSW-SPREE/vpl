# Virtual Production Line

Simulated production line for screen printed solar cells, Originally Published: 2002

Released as open source 2021.

This application was written almost 20 years ago and while best efforts have been made to get a working version some things inevitably won't work. For example, while the help html files are in this repository and are bundled with the application, they do not work due to dependencies on technologies that can't be made open source or are simply no longer available. (The original source code and build are preserved in a [branch in this repo](https://github.com/UNSW-SPREE/vpl/tree/legacy-build))

The instructions below are enough to build and run the application, so if you want to make some upgrades to the parts of the application that are no longer working, your contribuitions would be welcome. Just clone this repository, make your changes and make a pull request to request your changes to be integrated back into the main project for the benefit of all users of the Virtual Production Line.

## Installing and Running the VPL

### docker (Any Platform) - recommended
---

The docker version of this software has the advantage that it can run on any operating system or architecture that supports docker. This also means that it could be deployed as a hosted solution.

First install docker for your platform - https://docs.docker.com/get-docker/

Then run the following command to start the application using docker:

`docker run -p 8080:8080 ghcr.io/unsw-spree/vpl:release`

Point your browser to http://localhost:8080

### Windows
---
If you build the application acording to the instructions below, you will find that a zip file package is generated at vpl-package-windows/target/vpl-package-1.0-SNAPSHOT-bin.zip

The vpl can be run by extracting this zip file to a folder and running vpl.bat inside the extracted folder.

NOTE: the Virtual Production Line currently depends upon an old 16 bit x86 version of PC1D. Modern versions of Windows come in a variety of flavours and 64 bit windows does not natively support 16 bit applications. There are workarounds for these issues but they are beyond the scope of this readme.


## Building

### Locally 
---

1. Install Java 16 SDK
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

## Possible improvements

- Fix up the help files so they work inside the app - html files are all in this repository
- Make an installation package that works on Windows
    - Work out a way to run the 16 bit version of pc1d work (https://github.com/otya128/winevdm - doesn't seem to work) /or
    - upgrade the factory to use cmd- pc1d (https://www2.pvlighthouse.com.au/resources/PC1D/PC1Dmod6/PC1Dmod6.aspx)

## Contributors

- Stuart Wenham
- Anna Bruce
- Alison Lennon
- Gau Wei Hum
- Zhongtian Li
- Yang Li
- Quanzhou Yu

Conversion to open source, docker deployment and modern build process with thanks from Bram Hoex (UNSW), Richard Corkish (ACAP) and Ben Sudbury (PV Lighthouse)



