import boto.exception 
import boto.s3
from client import conn
from boto.s3.website import WebsiteConfiguration 
from xml.dom import minidom

def main(arg):
    try:
        bucket=conn.create_bucket(arg[0])
        k=bucket.new_key('error.html')
        k.set_contents_from_string('404testchttl<br><title>chttl</title>')
        k.set_acl('public-read')
        k=bucket.new_key('index.html')
        k.set_contents_from_string('<title>chttl</title>Hello World!')
        k.set_acl('public-read')
        
        config=WebsiteConfiguration()
        #Routing rules
        config.suffix='index.html'
        config.error_key='error.html'
        
        #RedirectAllRequestsTo NOT SUPPORT YET
        #RoutingRules=boto.s3.website.RoutingRules()
        #rule = boto.s3.website.RoutingRule()
        #rule.redirect=boto.s3.website.Redirect(
        #            hostname='www.google.com', protocol=None,
        #            replace_key=None,
        #            replace_key_prefix=None,
        #            http_redirect_code=None)
        #RoutingRules.add_rule(rule)
        
        #get bucket url
        #print bucket.get_website_endpoint()
        
        #config.routing_rules=RoutingRules
        #print "Put Website Configurations.."
        bucket.set_website_configuration(config)
        #print "Get Website Configurations.."
        #print repr(bucket.get_website_configuration())
        #print "Delete Website Configurations..\n"
        bucket.delete_website_configuration()
        
        
        
        #RedirectAllRequestsTo NOT SUPPORT YET
        #If 'redirect_all_requests_to' is set, all other attribute will be ignored!
        config2=WebsiteConfiguration()
        config2.suffix='index.html'
        #config2.redirect_all_requests_to=boto.s3.website.RedirectLocation(hostname='www.google.com')
        #print "Put Website Configurations with redirect all request"
        bucket.set_website_configuration(config2)
        #print "Get Website Configurations.."
        #print repr(bucket.get_website_configuration())
        #print "Delete Website Configurations..\n"
        bucket.delete_website_configuration()
        
        #print "\nClean up.."
        # clear bucket
        result = bucket.get_all_versions()
        for v in result:
            v.delete()
        
        conn.delete_bucket(bucket)
        #print " - Website Serial Test Done ! \n"
    except boto.exception.S3ResponseError, e:
        xmldoc = minidom.parseString(e.body)
        itemlist = xmldoc.getElementsByTagName('Message')
        print "Status Code: " + repr(e.status)
        print "Reason: " + repr(e.reason)
        print "Message: " + itemlist[0].childNodes[0].nodeValue
    