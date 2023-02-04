from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

class User(db.Model):
    id = db.Column(db.Integer, primary_key = True)
    name = db.Column(db.String(100))
    role = db.Column(db.String(100))
    phone = db.Column(db.String(70), unique = True)
    password = db.Column(db.String(80))
    registry_iat_datetime = db.Column(db.DateTime)
    registry_exp_datetime = db.Column(db.DateTime)
    jwt_token = db.Column(db.Text)