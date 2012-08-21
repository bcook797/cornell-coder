from operator import *

class QueryResult:
    def _init_(self, url, title, pagerank):
        self.url= url
        self.title= title
        self.pagerank= pagerank
    def _repr_(self):
        return repr((self.url, self.title, self.pagerank))

#Store file index into an array
f= open('index.txt', 'r')
index= []
line= f.readline()
while line != "":
    t= line
    t= t.lower()
    t= t.strip()
    index.append(t)
    line= f.readline()

#Store file pagerank into an array
f= open('pagerank.txt', 'r')
pagerank= []
line= f.readline()
while line != "":
    line= line.strip()
    p= float(line)
    pagerank.append(p)
    line=f.readline()

#Store file test3 into an array
f= open('test3.txt', 'r')
line= f.readline()
pages= []                
while line != "":
    string= line.split()
    url= string[1]
    pages.append(url)
    line= f.readline()

#Search index based on query inputed by user
a= 0
while a == 0:
    results= []
    term= raw_input("Enter Search Term: ")
    term= term.strip()    
    if term == "ZZZ":
        a= 1    
    else:
        term= term.lower()
        for i in range(len(pages)):
            url= pages[i]            
            if index[i].find(term) != -1:                               
                results.append((url, index[i], pagerank[i]))  
    if results == []:
        print "No results for: " + term
    else:
        results= sorted(results, key=lambda queryresult: queryresult[2], reverse= True)
        j= 0
        while j < 5 and j < len(results):
            print results[j]                            
            j= j + 1    
    