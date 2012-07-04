# -*- coding: utf-8 -*-
from BeautifulSoup import BeautifulSoup
import urllib2,math,json
import string

cursor = 0
recipes = []

for r in range(3):
	if cursor != 0:
		url = urllib2.urlopen("http://api.punchfork.com/recipes?key=7a99563e9099f8fa&cursor=" + str(cursor) + "&count=50")
	else:
		url = urllib2.urlopen("http://api.punchfork.com/recipes?key=7a99563e9099f8fa&count=50")

	j = json.load(url)
	recp = j['recipes']
	
	for re in range(len(recp)):
		recipes.append(recp[re])
	cursor = j['next_cursor']

urlTurkey = urllib2.urlopen("http://api.punchfork.com/recipes?key=7a99563e9099f8fa&q=turkey&count=5")
t = json.load(urlTurkey)
turkeyRecipes = t['recipes']

for tur in range(len(turkeyRecipes)):
	c = 0
	for recipe in recipes:
		if recipe['title'] == turkeyRecipes[tur]['title']:
			c = c + 1
	
	if c == 0:
		recipes.append(turkeyRecipes[tur])

urlHam = urllib2.urlopen("http://api.punchfork.com/recipes?key=7a99563e9099f8fa&q=ham&count=5")
h = json.load(urlHam)
hamRecipes = h['recipes']

for ham in range(len(hamRecipes)):
	c = 0
	for recipe in recipes:
		if recipe['title'] == hamRecipes[ham]['title']:
			c = c + 1
	
	if c == 0:
		recipes.append(hamRecipes[ham])
		
urlFish = urllib2.urlopen("http://api.punchfork.com/recipes?key=7a99563e9099f8fa&q=fish&count=5")
f = json.load(urlFish)
fishRecipes = f['recipes']

for fish in range(len(fishRecipes)):
	c = 0
	for recipe in recipes:
		if recipe['title'] == fishRecipes[fish]['title']:
			c = c + 1
	
	if c == 0:
		recipes.append(fishRecipes[fish])

f= open('recipes.txt',"w")
r= open('recipes.json','w')
ingred = open('ingredients.json','w')

dict = []
ingredict = []

print "Reading Recipes"
for recipe in recipes:
	myDict = {}
	print recipe['title'].encode('ascii', 'ignore')
	f.write(recipe['title'].encode('ascii', 'ignore'))
	myDict['name'] = recipe['title'].encode('ascii', 'ignore')
	
	rurl = recipe['pf_url']
	ingre =urllib2.urlopen(rurl).read()
	soup = BeautifulSoup(ingre)
	x=0
	ingredients = []
	for index in range(len(soup.findAll("span", { "class" : "ingredient-unit"}))):
			ingDict = {}
			iDict = {}
			f.write("\n"+soup.findAll("span", { "class" : "ingredient-name"})[index].string.encode('ascii', 'ignore'))	
			s = soup.findAll("span", { "class" : "ingredient-name"})[index].string.lower().replace('-',' ').encode('ascii', 'ignore')
			for c in string.punctuation:
				s = s.replace(c, '')
			ingDict['name'] = s
			iDict['name'] = s
			
			f.write(" "+soup.findAll("span", { "class" : "ingredient-n"})[index].string.encode('ascii', 'ignore'))
			f.write(" "+soup.findAll("span", { "class" : "ingredient-unit"})[index].string.encode('ascii', 'ignore'))
			ingDict['quantity'] = soup.findAll("span", { "class" : "ingredient-n"})[index].string.encode('ascii', 'ignore') 
			ingDict['unit'] = soup.findAll("span", { "class" : "ingredient-unit"})[index].string.lower().rstrip('s').encode('ascii', 'ignore')
			iDict['unit'] = soup.findAll("span", { "class" : "ingredient-unit"})[index].string.lower().rstrip('s').encode('ascii', 'ignore')
			
			ingredients.append(ingDict)
			
			count = 0
			for i in range(len(ingredict)):
				if iDict['name'] == ingredict[i]['name'] and iDict['unit'] == ingredict[i]['unit']:
					count = count + 1
			
			if count == 0:
				ingredict.append(iDict)
				
	
	myDict['ingredients'] = ingredients
	
	myDict['img'] = recipe['thumb']
	myDict['url'] = recipe['source_url']
	f.write("\nRecipe: "+recipe['source_url']+"\nImage: "+recipe['thumb'])
	f.write("\n\n")
	dict.append(myDict)
print""
print"Complete"

final = json.dumps(dict, indent=4)
finalIng = json.dumps(ingredict, indent=4)

r.write(final)
ingred.write(finalIng)
ingred.close()
r.close
f.close()
