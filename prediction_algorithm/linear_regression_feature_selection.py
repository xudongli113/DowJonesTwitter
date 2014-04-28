'''
Created on 5 Apr 2014

@author: tracysu1860
'''

import numpy as np 
import csv
from classifier import Classifier
from random import shuffle
import timeit
import sys
import pylab as pl
from sklearn import preprocessing
import result_plot as rp
from sklearn import feature_selection

def scale(data):
        
    scaled_x = preprocessing.scale(data)

    return scaled_x
    
def norm(data):
	return preprocessing.normalize(data, norm='l2')
	
start = timeit.default_timer()

# train ----------------------------------------
#senti_f = open('IBM/IBM.csv', 'r')
senti_f = open('Intel/Intel2.csv', 'r')
#f = open('data/sample_training.csv', 'rb')
senti_r = csv.reader(senti_f,delimiter="\t")
senti = np.array(list(senti_r))

senti_date = senti[:,0]
senti_val = scale(senti[:,1:].astype(float))

#stock_f = open('Stock/IBM.csv', 'rb')
stock_f = open('Stock/INTC.csv', 'rb')
stock_r = csv.reader(stock_f,delimiter="\t")
header = stock_r.next()
stock = np.array(list(stock_r))


stock_date = stock[:,0]

# 1.INTC.Open	2.INTC.High	3.INTC.Low	4.INTC.Close	5.INTC.Volume
stock_indicator = scale(stock[:,1:5].astype(float))
print'read data finished'
#new array of all data
alldata = []
select_date=[]
med_accuray = []
start_pre_date = 10

for index in range(0,len(stock)-1):
	date = stock_date[index]
	indicator = stock_indicator[index]
	close_val = indicator[3]
	
	if date in senti_date:
		select_date.append(date)
	
		sindex = list(np.where(senti_date==date))[0][0]
		senti = senti_val[sindex]
		#stock tends result, 1 represnts increase,  0 decrease
	
		delta = stock_indicator[index+1][3]-close_val
		if delta > 0:
			tends = 1
		else:
			tends =0
			
		features = list(indicator) + list(senti) + [tends]

		alldata.append(features)

alldata = np.array(alldata)
#print alldata
#-------------------------
#true_res = 'result/result.csv'
#out_csv = csv.writer(open(true_res,'wb'))
#out_csv.writerows(alldata)
#sys.exit()

predict = []
	
#predict market from day 8
for index in range(start_pre_date,len(alldata)):
	data = alldata[0:index,:]
	data_x = data[:,0:-1]
	data_y = data[:,-1]
	test = alldata[index,:]
	
	#-----------------------
	#features selection
	fs = feature_selection.SelectKBest(k=6)
	data_x=fs.fit_transform(data_x,data_y)
	feature_index = fs.get_support(indices=True)
	print feature_index
	test = np.take(test[0:-1],feature_index)	
	

	# ----------------------------------------
	# linear regression
	# ----------------------------------------

	classifier = Classifier(data_x,data_y,test)

	#using three classification method
	#1. logisticReg
	pre1 = classifier.logisticReg()[0]
	#2. svm
	pre2 = classifier.svm()[0]
	#3. knn
	pre3 = classifier.Gaussian_NaiveBayes()[0]
	predict.append([pre1,pre2,pre3])
	
test_data = alldata[start_pre_date:len(alldata),-1]

predict = np.array(predict)
#accuracy
miss_pre = []
accuracy = []
for index in range(len(predict[0])):
	temp = (predict[:,index] != test_data).sum()
	acc = 1-(temp*1.0/len(predict))
	miss_pre.append(temp)
	print acc
	accuracy.append(acc)


#print('missed predicted data are %d accuracy is %f'%(miss_pre,1-(miss_pre*1.0/len(predict))))
print'finished'
stop = timeit.default_timer()

print'out put result'
true_res = 'result/pre.csv'

out_csv = csv.writer(open(true_res,'wb'))
for index in range(len(predict)):
	out_csv.writerow([predict[index]])

#plot result pic
#rp.res_plot(test_data,predict,select_date)
#rp.accuracy_plot(accuracy)
print stop - start 
