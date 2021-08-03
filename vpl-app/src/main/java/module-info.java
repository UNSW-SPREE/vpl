module vpl.app {
    requires java.logging;
    requires java.prefs;

    requires transitive java.desktop;
    requires transitive java.xml;

    requires fmj;

    exports vpl;

}
