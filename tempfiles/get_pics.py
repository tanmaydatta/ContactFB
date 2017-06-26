import requests
import json
import gevent
from gevent import monkey; monkey.patch_all()

def get_url(id):
	return "https://graph.facebook.com/v2.9/"+id+"/picture?width=1000&access_token=EAAW2GpZBPSIMBACrZARmSlUbtluchmKeg3m0xnxQzOEBMzh6gxDCDhJO3RqArEj0NLZCJ1AHxZCVsY5dLxlE8aqZCFtXXTXti4E3RzuqDO2nHmygZAzn5GtqB2pPXha2pXlPQDqfWdOmOMlwaQDCBmGy50Ku0zOdGB1ZA0TFZBHzfgJOLthmyrcR3NA4BP9itaEZD"

def get_pic(id, out, key):

	try:
		# print(get_url(id))
		# pic_url = requests.get(get_url(id)).json()["data"]["url"]
		pic_url = get_url(id)
		if key.lower() in out.keys():
			out[key.lower()].append(pic_url)
		else:
			out[key.lower()]=[pic_url]
	except Exception as e:
		print(e)
		pass
	print(len(out.keys()))

data=json.load(open("pretty.json"))
out = {}
jobs=[]
for key, val in data.items():
	for id in val:
		jobs.append(gevent.spawn(get_pic, id, out, key))
	print(key)
	# break
gevent.joinall(jobs)
print(out)
f = open("pics.json", "w")
f.write(json.dumps(out))
f.close()
