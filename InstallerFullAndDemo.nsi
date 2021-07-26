; basic script template for NSIS installers
;
; Written by Philip Chu
; Copyright (c) 2004-2005 Technicat, LLC
;
; This software is provided 'as-is', without any express or implied warranty.
; In no event will the authors be held liable for any damages arising from the use of this software.
 
; Permission is granted to anyone to use this software for any purpose,
; including commercial applications, and to alter it ; and redistribute
; it freely, subject to the following restrictions:
 
;    1. The origin of this software must not be misrepresented; you must not claim that
;       you wrote the original software. If you use this software in a product, an
;       acknowledgment in the product documentation would be appreciated but is not required.
 
;    2. Altered source versions must be plainly marked as such, and must not be
;       misrepresented as being the original software.
 
;    3. This notice may not be removed or altered from any source distribution.


!define JRE_VERSION "1.7"
!define JRE_VERSION_OLD "1.6"
!define JRE "jre-7u5-windows-i586.exe"
!define JMF_VERSION "2.1.1e"
!define JMF "jmf-2_1_1e-windows-i586.exe"
 
!define setup "VPLSetupFullDemo.exe"
 
; change this to wherever the files to be packaged reside
!define srcdir "."
 
!define company "SPREE"
 
!define prodname "Virtual Production Line"
!define prodnameD "Virtual Production Line Demo"
!define exec "VPL.bat" ; "vpl.jar"
!define execDemo "VPLDemo.bat"

; optional stuff
 
; text file to open in notepad after installation
!define notefile "README.html"
 
; license text file
; !define licensefile license.txt
 
; icons must be Microsoft .ICO files
;!define icon "images\VirtualProductionLine.ico" changed in VPL version3
!define icon "icons\VPL3.ico"
 
; installer background screen
!define screenimage "images\cool.jpg"
 
; file containing list of file-installation commands
!define files "files.nsi"
 
; file containing list of file-uninstall commands
;!define unfiles "files.nsi"
;!define startmenu "C:\Documents and Settings\All Users\Start Menu\Programs\${prodname}"
!define startmenu "$SMPROGRAMS\${prodname}"
;!define desktop "C:\Documents and Settings\All Users\Desktop\"
!define desktop $DESKTOP
!define uninstaller "uninstall.exe"

;!define pc1d dir
!define programdataVPL "$APPDATA\Virtual Production Line" 
; registry stuff
 
!define regkey "Software\${prodname}"
!define uninstkey "Software\Microsoft\Windows\CurrentVersion\Uninstall\${prodname}"

 
;--------------------------------
 
;XPStyle on
ShowInstDetails hide
ShowUninstDetails hide
 
Name "${prodname}"
Caption "${prodname}"
 
!ifdef icon
Icon "${icon}"
!endif
 
OutFile "${setup}"
 
SetDateSave on
SetDatablockOptimize on
CRCCheck on
SilentInstall normal
 
InstallDir "$PROGRAMFILES\${prodname}"
InstallDirRegKey HKLM "${regkey}" ""
DirText "Setup will install Virtual Production Line in the following folder. \
To install in a different folder, click Browse and select another folder. Click Install to \
start the installer."

CompletedText "Installation of the Virtual Production Line is now complete."

!ifdef licensefile
LicenseText "License"
LicenseData "${srcdir}\${licensefile}"
!endif
 
; pages
; we keep it simple - leave out selectable installation types
 
!ifdef licensefile
Page license
!endif
 
; Page components
Page directory
Page instfiles
 
UninstPage uninstConfirm
UninstPage instfiles
 
;--------------------------------
 
AutoCloseWindow false
ShowInstDetails show
 
 
!ifdef screenimage
 
; set up background image
; uses BgImage plugin
 
Function .onGUIInit
	; extract background BMP into temp plugin directory
	InitPluginsDir
	File /oname=$PLUGINSDIR\1.bmp "${screenimage}"
 
	BgImage::SetBg /NOUNLOAD /FILLSCREEN $PLUGINSDIR\1.bmp
	BgImage::Redraw /NOUNLOAD
FunctionEnd
 
Function .onGUIEnd
	; Destroy must not have /NOUNLOAD so NSIS will be able to unload and delete BgImage before it exits
	BgImage::Destroy
FunctionEnd
 
!endif
 

; beginning (invisible) section
Section
	 
  WriteRegStr HKLM "${regkey}" "Install_Dir" "$INSTDIR"
  ; write uninstall strings
  WriteRegStr HKLM "${uninstkey}" "DisplayName" "${prodname} (remove only)"
  WriteRegStr HKLM "${uninstkey}" "UninstallString" '"$INSTDIR\${uninstaller}"'
 
!ifdef filetype
  WriteRegStr HKCR "${filetype}" "" "${prodname}"
!endif
 
  WriteRegStr HKCR "${prodname}\Shell\open\command\" "" '"$INSTDIR\${exec} "%1"'
  WriteRegStr HKCR "${prodname}\Shell\open\command\" "" '"$INSTDIR\${execDemo} "%1"'
 
!ifdef icon
  WriteRegStr HKCR "${prodname}\DefaultIcon" "" "$INSTDIR\${icon}"
!endif
 
  SetOutPath $INSTDIR
 
 
; package all files, recursively, preserving attributes
; assume files are in the correct places
;File /a "${srcdir}\${exec}"
;File /a "${srcdir}\${execDemo}"
 
!ifdef licensefile
File /a "${srcdir}\${licensefile}"
!endif
 
!ifdef notefile
File /a "${srcdir}\${notefile}"
!endif
 
!ifdef icon
File /a "${srcdir}\${icon}"
!endif
 
; dependencies
Call DetectJRE
Call DetectJMF

; any application-specific files
SetShellVarContext all
!ifdef files
!include "${files}"
!endif
 
  WriteUninstaller "${uninstaller}"
  
SectionEnd
 
; create shortcuts
Section
  
Call CreateRun

  CreateDirectory "${startmenu}"
  SetOutPath $INSTDIR ; for working directory
!ifdef icon
  CreateShortCut "${startmenu}\${prodname}.lnk" "$INSTDIR\${exec}" "" "$INSTDIR\${icon}"
  CreateShortCut "${startmenu}\${prodnameD}.lnk" "$INSTDIR\${execDemo}" "" "$INSTDIR\${icon}"

  CreateShortCut "${desktop}\${prodname}.lnk" "$INSTDIR\${exec}" "" "$INSTDIR\${icon}"
  CreateShortCut "${desktop}\${prodnameD}.lnk" "$INSTDIR\${execDemo}" "" "$INSTDIR\${icon}"

!else
  CreateShortCut "${startmenu}\${prodname}.lnk" "$INSTDIR\${exec}"
  CreateShortCut "${startmenu}\${prodnameD}.lnk" "$INSTDIR\${execDemo}"
!endif
 
!ifdef notefile
  CreateShortCut "${startmenu}\Release Notes.lnk "$INSTDIR\${notefile}"
!endif
 
!ifdef helpfile
  CreateShortCut "${startmenu}\Documentation.lnk "$INSTDIR\${helpfile}"
!endif
 
!ifdef website
WriteINIStr "${startmenu}\web site.url" "InternetShortcut" "URL" ${website}
 ; CreateShortCut "${startmenu}\Web Site.lnk "${website}" "URL"
!endif
 
!ifdef notefile
ExecShell "open" "$INSTDIR\${notefile}"
!endif

!ifdef uninstaller
CreateShortCut "${startmenu}\Uninstall Virtual Production Line.lnk" "$INSTDIR\${uninstaller}"
!endif

SectionEnd

;restart computer
Section
MessageBox MB_YESNO|MB_ICONQUESTION "After restarting your computer, the Virtual Production Line can be run from the Start Menu. Do you wish to restart your computer now?" IDNO +2
  Reboot
SectionEnd
 
; Uninstaller
; All section names prefixed by "Un" will be in the uninstaller
 
UninstallText "This will uninstall ${prodname}. You will need to uninstall the Java Media Framework separately if it is no longer required."
 
!ifdef icon
UninstallIcon "${icon}"
!endif
 
Section "Uninstall"
  SetShellVarContext all
  DeleteRegKey HKLM "${uninstkey}"
  DeleteRegKey HKLM "${regkey}"
  
  Delete "${startmenu}\*.*"
  Delete "${desktop}\${prodname}.lnk"
  Delete "${desktop}\${prodnameD}.lnk"
  RMDir "${startmenu}"
 
!ifdef licensefile
Delete "$INSTDIR\${licensefile}"
!endif
 
!ifdef notefile
Delete "$INSTDIR\${notefile}"
!endif
 
!ifdef icon
Delete "$INSTDIR\${icon}"
!endif
 
Delete "$INSTDIR\${exec}"
Delete "$INSTDIR\${execDemo}"

Delete "${programdataVPL}\pc1d\*.*"
Delete "${programdataVPL}\*.*"
GetFullPathName /SHORT $R8 "${programdataVPL}"

RMDir /REBOOTOK /r $R8\pc1d
RMDir /REBOOTOK /r $R8

RMDir /REBOOTOK /r $INSTDIR\help
RMDir /REBOOTOK /r $INSTDIR\icons
RMDir /REBOOTOK /r $INSTDIR\images
RMDir /REBOOTOK /r $INSTDIR\licence
RMDir /REBOOTOK /r $INSTDIR\pc1d

Delete /REBOOTOK "$INSTDIR\*.*"
RMDir /REBOOTOK $INSTDIR



SectionEnd

Function GetJMF
        MessageBox MB_OK "The ${prodname} uses the Java Media Framework (JMF), it will now \
be installed. It must be installed in the default directory. Do not restart \
 your computer when the JMF installation is complete. Wait until the entire \
Virtual Production Line installation is complete before restarting."
	SetOutPath $TEMP
  	File ${JMF}
        ExecWait $TEMP\${JMF}
FunctionEnd
 
 
Function DetectJMF
  ReadRegStr $2 HKLM "SOFTWARE\Sun Microsystems, Inc.\JMF" \
             "LatestVersion"
  
  StrCmp $2 ${JMF_VERSION} done
  
  Call GetJMF
  
  done:
FunctionEnd

Function GetJRE
        MessageBox MB_OK "${prodname} uses Java 1.7, it will now \
                         be installed"
	SetOutPath $TEMP
  	File ${JRE}
        ExecWait $TEMP\${JRE}
FunctionEnd
 
 
Function DetectJRE
  ReadRegStr $2 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" \
             "CurrentVersion"
  StrCmp $2 ${JRE_VERSION} done
  StrCmp $2 ${JRE_VERSION_OLD} done
  
  Call GetJRE
  
  done:
FunctionEnd


Function CreateRun

  ReadRegStr $2 HKLM "SOFTWARE\Sun Microsystems, Inc.\JMF\${JMF_VERSION}" \
             "JMFDir"
  
  ReadRegStr $R1 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" \
             "CurrentVersion"
  ReadRegStr $R0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\$R1" \
             "JavaHome"
  StrCpy $R0 "$R0\bin"
  
  GetFullPathName /SHORT $R2 $R0


  
 FileOpen $0 $INSTDIR\VPL.bat w
 FileWrite $0 "@echo off$\r$\n" 
 FileWrite $0 "start $R2\javaw -classpath VPL.jar;$2\lib\jmf.jar;$2\lib\sound.jar; VirtualProductionLine$\r$\n"
 FileClose $0 

 FileOpen $0 $INSTDIR\VPLDemo.bat w
 FileWrite $0 "@echo off$\r$\n" 
 FileWrite $0 "start $R2\javaw -classpath VPL.jar;$2\lib\jmf.jar;$2\lib\sound.jar; VirtualProductionLine true$\r$\n"
 FileClose $0 
  done:
FunctionEnd

