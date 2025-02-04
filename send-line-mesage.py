import requests


buildDoneMsg = "MMB Build Done."
urlMsg = "https://line-bot-test-mmb.onrender.com/?msg={}".format(buildDoneMsg)

response = requests.get(urlMsg)
print(response.text)