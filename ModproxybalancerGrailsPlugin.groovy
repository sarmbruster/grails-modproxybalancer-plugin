class ModproxybalancerGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [ :] // tomcat: "1.3.2.BUILD-SNAPSHOT" // no hard dependency
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Stefan Armbruster, Davide Rossi"
    def authorEmail = "stefan@armbruster-it.de, drossi@byte-code.com "
    def title = "Manage an Apache mod_proxy_balancer LoadBalancer during a clusterwide application redeployment"
    def description = '''
This plugin assumes you have an Apache mod_proxy_balancer running in front of multiple tomcat instances in order to
provide a) high-availability and b) load-balancing to your Grails application. In such a scenario, upgrading the running
application to a newer release is painful. This plugin's goal is to simplify that procedure by performing a
"rolling upgrade". Calling 'grails tomcat redeploy' performs these actions:

1) take first node offline in loadbalancer
2) undeploy app on first tomcat node
3) deploy app on first tomcat node
4) check if app responds on first tomcat node
5) take first node online in loadbalancer
6) proceed the same procedure with next node

Since Grails 1.3.2 the tomcat plugin is capable of
a) deploying to multiple hosts and
b) emitting lifecycle events: PreDeploy, PostDeploy, PreUndeploy, PostUndeploy

By catching up these events it is simple possible to remote control the loadbalancer. mod_proxy_balancer comes with a
simple management frontend called balancer-manager. This consists of some simple HTML forms that can be easily
triggered using htmlunit.

This plugin was invented on the Hackergarden event at gr8conf 2010 in Copenhagen and finished the weekend afterwards.
Thanks to Canoo for sponsoring pizza and drinks.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/modproxybalancer"

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
