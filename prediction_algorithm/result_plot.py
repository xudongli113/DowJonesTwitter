import numpy as np
import matplotlib.pyplot as plt
from sklearn.metrics import roc_curve, auc
models = ['logisticReg','svm','Gaussian Bayes'] 

def res_plot(true,pre,data):
	
	N = len(pre)
	iniarray = [1]*N

	ind = np.arange(N)  # the x locations for the groups
	width = 1       # the width of the bars
	
	for index in range(len(pre[0])):
		
		false_res = np.subtract(true,pre[:,index])
		false_res = np.absolute(false_res)
		true_res = np.subtract(iniarray,false_res)
		
		ax = plt.subplot(3,1,index+1)
		rects1 = ax.bar(ind, false_res, 1, color='#E92424')

		rects2 = ax.bar(ind, true_res, 1, color='#54D816')

		# add some
		
		ax.set_ylabel('trends')
		
		if index==0:
			ax.set_title('prediction result')
			ax.legend( (rects1[0], rects2[0]), ('wrong', 'correct'))
		ax.set_xticks(ind+width)
		ax.set_xticklabels( range(N) )
		
		
		plt.xlabel(models[index])
		

	plt.show()
	
def accuracy_plot(accuracy):
	N = len(accuracy)
	ind = np.arange(N)
	width=0.5
	fig,ax = plt.subplots()
	rect = ax.bar(ind,accuracy,width,align='center',alpha="0.4")
	ax.set_xticks(ind)
	ax.set_xticklabels( models )
	plt.show()
	
def ROC_plot(true,pre):
	N=len(pre[0])
	for index in range(N):
		print true
		print pre[:,index]
		fpr, tpr, thresholds = roc_curve(true, pre[:,index])

		roc_auc = auc(fpr, tpr)
		plt.plot(fpr, tpr, lw=1, label='%s auc = %0.2f' %  (models[index],roc_auc))
	plt.legend(loc="lower right")
	plt.show()
	
