import json

file = open("fmsca_buses.txt")
bus = open("bus.json", 'w')

r = 0
sms_labels = []
sms_info = []
sms_dict = []

for line in file:
	if (r == 0):		
		sms = line.split(',')
		for j in range(len(sms)):
			label = sms[j].strip('"').strip('\n').strip('""')
			sms_labels.append(label)
			print label
	
	if (r != 0):
		info = line.split(',')
		print r
		print "INFO", info
		myDict = {}
		for k in range(len(sms_labels)):
			myDict[sms_labels[k]]= info[k].strip('\n')
			print sms_labels[k],":",info[k]			
		
		sms_dict.append(myDict)
		
	r = r + 1
	
for t in range(len(sms_dict)):
	print '<------------------------------------------------------------------>'
	print sms_dict[t]['DOT_NUMBER']
	print "DICTIONARY:", sms_dict[t]
	print " "
	

dict = json.dumps(sms_dict, sort_keys=True, indent=4)
print dict

bus.write(dict)







