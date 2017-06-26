import json
import requests
f = open("contactdump")
fw = open("contactlist", "w")
js = json.load(open("pics.json"))

lines = f.readlines()

def save_image(name, pic_url):
    with open("pics/" + name +'.jpg', 'wb') as handle:
        response = requests.get(pic_url, stream=True)

        if not response.ok:
            print response

        for block in response.iter_content(1024):
            if not block:
                break

            handle.write(block)
    print name

for line in lines:
    # print line.split(": ")[-1]
    name = str(line.split(": ")[-1][:-1])
    
    try:
        if name in js.keys():
            # print "hee"
            print name
            url = js[name][0]
            pic_name = name.replace(" ", "_")
            save_image(pic_name, url)
    except:
        pass

f.close()
fw.close()