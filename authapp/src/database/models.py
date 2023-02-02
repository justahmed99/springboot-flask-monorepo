from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

class User(db.Model):
    id = db.Column(db.Integer, primary_key = True)
    name = db.Column(db.String(100))
    role = db.Column(db.String(100))
    phone = db.Column(db.String(70), unique = True)
    password = db.Column(db.String(80))