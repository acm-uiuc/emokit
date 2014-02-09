from sklearn.cross_validation import cross_val_score
from sklearn.datasets import make_blobs
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import ExtraTreesClassifier
from sklearn.tree import DecisionTreeClassifier

class RandomizedTrees(object):
  # Initialize classifier's global variables.
  # labels:  label space
  def __init__(self, labels):
    self.labels = labels
    self.X = []
    self.y = []
    self.clf = RandomForestClassifier(n_estimators=10, max_depth=None, min_samples_split=1, random_state=0)
#    self.clf.fit(14 * [0], 'dummy')

  # Add training example to training data set. 
  # Should not be called after Train is called.
  def AddTrainExample(self, example, label):
    self.X.append(example)
    self.y.append(label)

  # Train on available data (as added by AddTrainExample). 
  # Should be called only after all training data has been input. Also, should
  # only be called once.
  def Train(self):
    self.clf.fit(self.X, self.y)

  # Classify example.
  # Should only be called after all training data has been input 
  # (AddTrainExample) and the classifier has been trained on the data (Train).
  # If called before Train, 'classes_' attribute of RandomForestClassifier will
  # not be initialized.
  def Classify(self, example):
    label = self.clf.predict(example)
    score = self.clf.score(example, label)
    return (label, score)
