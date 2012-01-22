!include "MUI.nsh"

!define JRE_VERSION "1.6"
!define JRE_URL "http://javadl.sun.com/webapps/download/AutoDL?BundleId=58134"

!include "FileFunc.nsh"
!insertmacro GetFileVersion
!insertmacro GetParameters
!include "WordFunc.nsh"
!insertmacro VersionCompare

Name "${project.name}"
OutFile "${project.build.directory}\${project.artifactId}-${project.version}.exe"
RequestExecutionLevel admin
SetCompressor bzip2
InstallDir $PROGRAMFILES64\${project.name}

!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH

!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES
!insertmacro MUI_UNPAGE_FINISH

!insertmacro MUI_LANGUAGE "English"
!insertmacro MUI_LANGUAGE "German"

LangString JavaInstall ${LANG_ENGLISH} "${project.name} needs the Java Runtime Environment version ${JRE_VERSION} or newer but it is not installed on your system. Do you want to automatically download and install it? Press 'No' if you want to install Java manually later."
LangString JavaInstall ${LANG_GERMAN} "${project.name} braucht die Java Laufzeit Umgebung Version ${JRE_VERSION} oder neuer. Wollen Sie sie jetzt automatisch herunterladen und installieren? Klicken Sie 'Nein' um Java nach der Installation von ${project.name} selbst zu installieren."

Function GetJRE
  MessageBox MB_YESNO|MB_ICONQUESTION $(JavaInstall) IDNO done
  StrCpy $2 "$TEMP\Java Runtime Environment.exe"
  nsisdl::download /TIMEOUT=30000 ${JRE_URL} $2 
  Pop $R0
  StrCmp $R0 "success" +3
  MessageBox MB_OK "Error: $R0"
  Quit
  ExecWait "$2"
  Delete "$2"
  done:
FunctionEnd

Function DetectJRE
  ${GetFileVersion} "$SYSDIR\javaw.exe" $R1
  ${VersionCompare} ${JRE_VERSION} $R1 $R2
  StrCmp $R2 0 done
  StrCmp $R2 2 done
  Call GetJRE
  done:
FunctionEnd

Section
  Call DetectJRE
  SetOutPath $INSTDIR
  WriteUninstaller "$INSTDIR\Uninstall.exe"
  
  File ${basedir}\README.txt
  File ${basedir}\LICENSE.txt
  File ${basedir}\src\main\nsis\${project.name}.ico
  SetOutPath $INSTDIR\lib
  File lib\*.jar
  File /oname=${project.artifactId}.jar ${project.build.directory}\${project.artifactId}-${project.version}.jar
  CreateShortCut "$INSTDIR\${project.name}.lnk" \
    "$SYSDIR\javaw.exe" "-jar $\"$INSTDIR\lib\${project.artifactId}.jar$\"" "$INSTDIR\${project.name}.ico" 0 SW_SHOWNORMAL
  CreateShortCut "$SMPROGRAMS\${project.name}.lnk" \
    "$SYSDIR\javaw.exe" "-jar $\"$INSTDIR\lib\${project.artifactId}.jar$\"" "$INSTDIR\${project.name}.ico" 0 SW_SHOWNORMAL
  CreateShortCut "$DESKTOP\${project.name}.lnk" \
    "$SYSDIR\javaw.exe" "-jar $\"$INSTDIR\lib\${project.artifactId}.jar$\"" "$INSTDIR\${project.name}.ico" 0 SW_SHOWNORMAL

  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${project.name}" "DisplayName" "${project.name}"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${project.name}" "DisplayVersion" "${project.version}"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${project.name}" "DisplayIcon" "$INSTDIR\${project.name}.ico"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${project.name}" "UninstallString" "$INSTDIR\Uninstall.exe"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${project.name}" "Publisher" "${project.organization.name}"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${project.name}" "HelpLink" "${project.url}"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${project.name}" "URLInfoAbout" "${project.url}"
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${project.name}" "NoModify" 1
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${project.name}" "NoRepair" 1
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${project.name}" "InstallLocation" "$INSTDIR"
  ${GetSize} "$INSTDIR" "/S=0K" $0 $1 $2
  IntFmt $0 "0x%08X" $0
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${project.name}" "EstimatedSize" $0
SectionEnd

Section "Uninstall"
  RMDir /r "$INSTDIR\lib"
  Delete "$INSTDIR\${project.name}.lnk"
  Delete "$INSTDIR\${project.name}.ico"
  Delete "$INSTDIR\README.txt"
  Delete "$INSTDIR\LICENSE.txt"
  Delete "$INSTDIR\Uninstall.exe"
  RMDir "$INSTDIR"
  Delete "$SMPROGRAMS\${project.name}.lnk"
  Delete "$DESKTOP\${project.name}.lnk"
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${project.name}"
SectionEnd
