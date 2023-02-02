import os

class Config(object):
    DEBUG = True
    TESTING = False
    CSRF_ENABLED = True
    JWT_SECRET_KEY = 'your secret key'
    SQLALCHEMY_DATABASE_URI = 'sqlite:///Database.db'
    SQLALCHEMY_TRACK_MODIFICATIONS = True
    FLASK_APP = os.environ.get('FLASK_APP')

class ProductionConfig(Config):
    DEBUG = False

class StagingConfig(Config):
    DEVELOPMENT = True
    DEBUG = True

class DevelopmentConfig(Config):
    DEVELOPMENT = True
    DEBUG = True

class TestingConfig(Config):
    TESTING = True
