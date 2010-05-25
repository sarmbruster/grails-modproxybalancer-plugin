import com.gargoylesoftware.htmlunit.WebClient

eventPreUndeploy = {
    def index = config.tomcat.deploy.url.indexOf(it)
    assert index != -1, "could not find $it in config.tomcat.deploy.url (Config.groovy)"
    def tomcatNode = config.tomcat.deploy.balancerMapping[index]
    println "setting $tomcatNode to state offline"
    loadbalancerNotify(tomcatNode, false, config.tomcat.deploy.balancerUrl)
}

eventPostDeploy = {

    checkDeployment(it, serverContextPath)

    def index = config.tomcat.deploy.url.indexOf(it)
    assert index != -1, "could not find $it in config.tomcat.deploy.url (Config.groovy)"
    def tomcatNode = config.tomcat.deploy.balancerMapping[index]
    println "setting $tomcatNode to state online"
    loadbalancerNotify(tomcatNode, true, config.tomcat.deploy.balancerUrl)

}


/**
 * disable or enable a given tomcat node in an Apache mod_proxy_balancer loadbalancer instance
 * @param url
 * @param state
 * @return
 */
def loadbalancerNotify(String ajpUrl, boolean state, String balancerUrl) {

    /*def matcher = url =~ /:\/\/(\w+):/
    def host = matcher[0][1]
    String ajpUrl = "ajp://$host:8009"*/

    def webClient = new WebClient()
    def page = webClient.getPage(balancerUrl);
    def formpage = page.getFirstAnchorByText(ajpUrl).click()

    def form = formpage.getFormByName("")
    def rb = form.getRadioButtonsByName("dw")
    //println rb
    def action = state ? "Enable" : "Disable"
    rb.each {
        //println it
        it.setChecked(it.valueAttribute == action)
    }
    def result = form.getInputByValue("Submit").click()
    assert 200 == result.webResponse.statusCode
    //println result.asXml()
}

def checkDeployment(String managerUrl, String contextPath) {
    def matcher = managerUrl =~ /(.*)\/manager/
    def applicationURL = "${matcher[0][1]}/$contextPath"
    def webClient = new WebClient()
    def page = webClient.getPage(applicationURL)
    assert 200 == page.webResponse.statusCode, "deployed application does not respond. Red alert!"
    println "$applicationURL is alive"
}
