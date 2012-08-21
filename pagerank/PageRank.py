import urllib
import urllib2
from BeautifulSoup import BeautifulSoup

#Adds a link score to the link matrix
def addLink(link, r):
    t= 0    
    while t < len(pages):
        if link == pages[t]:
            link_matrix[r][t]= 1.                 
            t= len(pages)        
        t= t+1    
    
#Creates the correct link out of the different forms of URLs
def createLink(domain,link):    
    if link.find("http") == -1:
        i= 0
        j= 0
        while i == 0:
            if link.find("../") != -1:
                link= link[link.find("/")+1:] 
                j= j + 1                                     
            elif link.find("/") != -1 and link.find("/") == 0:
                link= link[link.find("/")+1:]                        
                i= 1
            elif j == 0:
                i= 1
            else:
                i= 1
        while j > 0:
            domain= domain[:domain.rfind("/")]
            j= j - 1
        link= domain + "/" + link              
    elif link.find("#") != -1:
        link= link[:link.find("#")]      
    return link

# Create Index of page titles and writes them to a file
def createIndex(pages, title_text):
    for i in range(len(pages)):
        url= pages[i]
        try:
            sock= urllib2.urlopen(url)
            soup= BeautifulSoup(sock)
        except IOError:
            print "Cannot Open Page: " + url
        title= soup.find("title")
        try:
            title= title.contents[0]
            title= title.strip()
        except AttributeError:
            title= "Error with extracting Title"
        try:
            ptitle= soup.find("span", {"class" : "pageTitle"})
            try:
                ptitle= ptitle.contents[2]
                ptitle= str(ptitle).strip()
            except IndexError:
                ptitle= ptitle.contents[0]
                ptitle= str(ptitle).strip()
            t= title + ": " + ptitle
        except AttributeError:
            t= title
        title_text[url]= t.strip()
    f= open("index.txt", "w")
    for j in range(len(pages)):
        url= pages[j]
        title= title_text[url]
        f.write(str(title) + "\n")
    
#Write the pages and there info to a file called metadata
def metadata(page_rank, anchor_text, pages):
    f= open("metadata.txt", "w")
    f.write("Page ID\tPage Rank\tAnchor Text\tURL\n")
    for i in range(len(pages)):
        id= i+1
        prank= page_rank[i]
        text= anchor_text[pages[i]]
        url= pages[i]
        f.write(str(id) + "\t" + str(prank) + "\t" + str(text) + "\t" + url + "\n")
        
#Write the page rank of each page to a file called prdata
#def prdata(page_rank):

#Find the pageRank of each page and writes them to a file
def pageRank(pages, page_rank, link_matrix):
    numLinks= [0]*len(link_matrix)
    outLinks= link_matrix
    for i in range(len(outLinks)):        
        n= 0
        for j in range(len(outLinks)):
            if outLinks[i][j] == 1:
                n= n + 1
        
        numLinks[i]= n
    alpha= 0.8
    pmatrix= [alpha/len(pages)]*len(pages)
    t= 0
    while t < 10:
        for i in range(len(page_rank)):
            r= 0
            for j in range(len(link_matrix)):
                if link_matrix[j][i] > 0:
                    r= r + page_rank[j]/numLinks[j]
            if r == 0:
                r= 1.
            page_rank[i]= (1 - alpha)*r + pmatrix[i]
        t= t + 1
    f= open("pagerank.txt", "w")
    for j in range(len(pages)):
        p= page_rank[j]
        f.write(str(p) + "\n")
        

#Open and initialize the test file
f= open('test3.txt', 'r')
line= f.readline()
pages= []
title_text= {}
a= 0                
                
while line != "":
    string= line.split()
    url= string[1]
    pages.append(url)
    line= f.readline()
    a= a+1

#Create link matrix with all zeroes
link_matrix= [0]*len(pages)
for q in range(len(link_matrix)):
    link_matrix[q]= [0]*len(pages)

anchor_text= {}
for r in range(len(pages)):
    anchor_text[pages[r]]= ""
    
#Create link matrix and extract anchor text
for r in range(len(pages)):    
    domain= pages[r]  
    domain= domain[:domain.rfind("/")]       
    try:
        sock= urllib2.urlopen(pages[r])
        soup= BeautifulSoup(sock)
    except IOError:
        print "Cannot Open Link: " + pages[r] 
    
    hyperlinks= soup.findAll("a")        
    for s in range(len(hyperlinks)):
        link= str(hyperlinks[s])
        link= link[link.find("\"") + 1:]
        link= link[:link.find("\"")]  
        text= hyperlinks[s].contents
        text= str(text)
        if text.find("alt=") != -1:
            text = text[text.find("alt=")+5:]
            text = text[:text.find("\"")]    
        text = text.strip()                    
        link= createLink(domain, link)        
        addLink(link, r)                 
        c= 0        
        while c < len(pages):
            if link == pages[c]:
                if anchor_text[link].find(text) == -1:
                    anchor_text[link]= anchor_text[link] + "...." + text
            c= c + 1    

#Store anchor text
for q in range(len(pages)):
    if anchor_text[pages[q]] != "":
        r= anchor_text[pages[q]].split("....")
        del r[0]
        for s in range(len(r)):            
            r[s]= r[s].replace("'u", "")
            r[s]= r[s].replace("'", "")
            r[s]= r[s].replace("[", "")
            r[s]= r[s].replace("]", "")
            r[s]= r[s].replace("\\r", "")
            r[s]= r[s].replace("\\n", "")
        anchor_text[pages[q]]= r        

print anchor_text
createIndex(pages, title_text)
page_rank= [1]*len(pages) 
pageRank(pages, page_rank, link_matrix)
print page_rank
metadata(page_rank, anchor_text, pages)

    
        
        
        
        
        
