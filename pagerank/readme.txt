Brandon Cook
Information Retrieval
PageRank

PAGERANK:

The PageRank.py program is what is used to extract the hyperlinks from the input data, calculate the PageRank of each page, create the index, and write the metadata file.  In order to open each webpage the program imports the urllib and the urllib2 python packages. The program also uses the BeautifulSoup package, which helps to parse the html to locate specific anchor tags.  In PageRank there are several defined methods that help to perform different tasks. The createLink method helps to build the correct link because the links within the anchor tags are not the specific links provided in the pages. After createLink builds the correct link then addLink adds it the link matrix, which is stored as a multi-dimensional array. After the link matrix is formed the method pageRank calculates the pageRank of each page and stores them into an array. The pageRanks are then written to a file pagerank.txt for storage to be used during searches. The createIndex function extracts the titles and anchors corresponding to each page and stores them into a associative array where the key is the title and the value is the actual url of the page. The index is then written to a file index.txt for storage to be used during searches. The metadata function then writes the pageID, PageRank, Anchor Text, and url onto a file metadata.txt. 

SEARCH:

The search.py program is what is actually used to search each page depending on a specific query. The search.py program takes the files created by the PageRank.py program and stores their values into an array. The program then asks for the user to enter a search term. The search term is entered and then each page that matches that search term is stored in an array called results. The results are then sorted according to PageRank and the first five results are posted. The postings consist of the url of the page its title and its PageRank.

INSTRUCTIONS:

1.	Open your command line to the correct file directory
                      Ex: cd \Desktop\a3
2.	Ensure that you have python 2.7 or higher installed on your system
3.	Type search.py and begin entering your queries



TESTS:

Enter Search Term: colloquium
('http://www.infosci.cornell.edu/about/colloquiumFA09/Oct%2014.html', 'cornell information science: information science colloquium', 0.202787456446)
('http://www.infosci.cornell.edu/about/colloquiumFA09/Oct%205.html', 'cornell information science: information science colloquium', 0.202787456446)
('http://www.infosci.cornell.edu/about/colloquiumFA09/Sept%2010.html', 'cornell information science: information science colloquium', 0.202787456446)
('http://www.infosci.cornell.edu/about/colloquiumFA09/Sept%202.html', 'cornell information science: information science colloquium', 0.202787456446)
('http://www.infosci.cornell.edu/about/colloquiumFA09/Sept%2023.html', 'cornell information science: information science colloquium', 0.202787456446)

Enter Search Term: major
('http://www.infosci.cornell.edu/ugrad/CALSMajor.htm', 'cornell information science: the information science major', 0.202787456446)
('http://www.infosci.cornell.edu/ugrad/index.html', 'cornell information science
: undergraduate majors', 0.193654251978)
('http://www.infosci.cornell.edu/ugrad/CALSRequirements.htm', 'cornell information science: major requirements', 0.00933328545791)
('http://www.infosci.cornell.edu/ugrad/ISSTRequirements.htm', 'cornell information science: major requirements', 0.00728847941104)
('http://www.infosci.cornell.edu/ugrad/ArtsRequirements.htm', 'cornell information science: major requirements', 0.006619152241)

Enter Search Term: hadoop
('http://www.infosci.cornell.edu/hadoop/index.html', 'cornell web lab: hadoop', 0.00766986376661)
('http://www.infosci.cornell.edu/hadoop/mac.html', 'cornell web lab: hadoop', 0.
00330477207514)
('http://www.infosci.cornell.edu/hadoop/windows.html', 'cornell web lab: hadoop'
, 0.00330477207514)
('http://www.infosci.cornell.edu/hadoop/docs.html', 'cornell web lab: hadoop', 0
.00309822382212)
('http://www.infosci.cornell.edu/hadoop/cac.html', 'cornell web lab: hadoop', 0.
00294085372311)

Enter Search Term: alumni
('http://www.infosci.cornell.edu/graduateStudents/alumni.html', 'cornell information science: information science alumni', 0.00564151270168)


