application {
    title = 'desktop'
    startupGroups = ['desktop']
    autoShutdown = true
}
mvcGroups {
    // MVC Group for "desktop"
    'desktop' {
        model      = 'org.remotecontrol.DesktopModel'
        view       = 'org.remotecontrol.DesktopView'
        controller = 'org.remotecontrol.DesktopController'
    }
}