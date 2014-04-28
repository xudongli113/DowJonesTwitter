'''
Created on 5 Apr 2014

@author: tracysu1860
'''

from sklearn import preprocessing
from sklearn import linear_model
from sklearn.naive_bayes import GaussianNB
from sklearn import tree
from sklearn.neighbors import KNeighborsClassifier
from sklearn import svm
from sklearn.lda import LDA
from sklearn.grid_search import GridSearchCV
import sys

class Classifier():
        
    def __init__(self,data_x,data_y,test):
#        self.data_x = self.scale(data_x)
#        self.data_y = data_y
#        self.test = self.scale(test)
        
        self.data_x = data_x
        self.data_y = data_y
        self.test = test
    
    
    # Scaling
    def scale(self,data):
        
        scaled_x = preprocessing.scale(data)
    
        return scaled_x
    
    # linear regression 
    def linearReg(self):
        
        regr = linear_model.LinearRegression()
        model = regr.fit(self.data_x, self.data_y)
        pre = regr.predict(self.test)
        
        return pre
                 
    def logisticReg(self):
		
		regr = linear_model.LogisticRegression()
		regr = regr.fit(self.data_x,self.data_y)
		pre = regr.predict(self.test)

		return pre
    	
    def Gaussian_NaiveBayes(self):
        gnb = GaussianNB()
        model = gnb.fit(self.data_x,self.data_y)
        y_predict = model.predict(self.test)
        
        return y_predict
    
    
    def Decision_tree(self):
        
        clf = tree.DecisionTreeClassifier()
        clf = clf.fit(self.data_x, self.data_y)
        y_predict = clf.predict(self.test)
        print y_predict[0]

        return y_predict[0]
    
    def knn(self):
        neigh = KNeighborsClassifier(n_neighbors=2,algorithm = 'brute')
        clf = neigh.fit(self.data_x, self.data_y)
        y_predict = clf.predict(self.test)
        
        return y_predict
    
    def lda(self):
    	clf = LDA()
    	model = clf.fit(self.data_x,self.data_y)
    	pre = model.predict(self.test)
    	
    	return pre
    
    def svm(self):
		tuned_parameters = [
				    {'kernel': ['linear'], 'C': [0.01,0.1,1, 10, 100]}]
		clf = GridSearchCV(svm.SVC(C=1), tuned_parameters, cv=5, scoring='precision')

		clf.fit(self.data_x, self.data_y)	 
		pre = clf.predict(self.test)
		print("Best parameters set found on development set:")
		print()
		print(clf.best_estimator_)
		print()
		print("Grid scores on development set:")
		print()
		for params, mean_score, scores in clf.grid_scores_:
		    print("%0.3f (+/-%0.03f) for %r"
		          % (mean_score, scores.std() / 2, params))
		
		print("Detailed classification report:")
		return pre
        
    
