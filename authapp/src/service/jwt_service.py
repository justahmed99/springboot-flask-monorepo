from src.database.models import User
from werkzeug.security import generate_password_hash, check_password_hash
from app import db
import datetime
import os
import jwt

class JwtService :
    def __init__(self, jwt_repository):
        self.jwt_repository = jwt_repository

    def generate_token(self, user: User, password_input: str):
        return self.jwt_repository.generate_token(user, password_input)
    
    def jwt_decode(self, token):
        return self.jwt_repository.jwt_decode(token)

class JwtRepository :
    def generate_token(self, user: User, password_input: str) :
        status = False
        token = None
        if check_password_hash(user.password, password_input):
            status = True
            # generate JWT Token
            if user.jwt_token == None :
                current_date = datetime.datetime.utcnow()
                user.registry_iat_datetime = current_date
                user.registry_exp_datetime = user.registry_iat_datetime + datetime.timedelta(minutes=int(os.getenv("JWT_VALIDITY_TIME")))
                token = jwt.encode(
                    {
                        "name": user.name,
                        "phone": user.phone,
                        "role": user.role,
                        "iat": user.registry_iat_datetime,
                        "exp": user.registry_exp_datetime,
                    },
                    os.getenv("JWT_SECRET_KEY"),
                )

                user.jwt_token = token

                db.session.add(user)
                db.session.commit()
        
        return status, user.jwt_token

    def jwt_decode(self, token) :
        return jwt.decode(token, os.getenv("JWT_SECRET_KEY"), algorithms=["HS256"])