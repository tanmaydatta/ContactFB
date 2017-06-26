from bs4 import BeautifulSoup as bs
import json
f=open("friends.html")
html = f.read()
soup=bs(html)
prettyHTML=soup.prettify()   #prettify the html

print(prettyHTML)

pretty = open("pretty.json", "w")
oj = {}
# pretty.write(prettyHTML.encode('utf-8'))
divs=soup.find_all("div", { "class" : "fsl fwb fcb" })
for div in divs:
	try:
		name = div.a.text
		j = json.loads(div.a["data-gt"])
		id = j["engagement"]["eng_tid"]
		if name in oj.keys():
			oj[name].append(id)
		else:
			oj[name]=[id]
		print(name)
	except:
		pass
pretty.write(json.dumps(oj))