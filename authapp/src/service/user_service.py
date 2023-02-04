from app import db
from src.database.models import User
from werkzeug.security import generate_password_hash
from src.utlis.string_utils import get_random_string
import os
import datetime

class UserService:
    def __init__(self, user_repository):
        self.user_repository = user_repository

    def get_user_by_phone(self, phone_number):
        return self.user_repository.get_by_phone(phone_number)

    def get_user_by_phone_without_token(self, phone_number):
        return self.user_repository.get_by_phone_without_token(phone_number)

    def create_user(self, user_data):
        return self.user_repository.create_user(user_data)
    
    def update_user(self, user: User, user_json):
        return self.user_repository.update_user(user, user_json)
    
    def register(self, data):
        return self.user_repository.register(data)

class UserRepository:
    def get_by_phone(self, phone_number):
        user = User.query.filter_by(phone=phone_number).first()
        return user

    def get_by_phone_without_token(self, phone_number):
        user = db.session.query(User).filter(User.phone == phone_number).filter(User.registry_exp_datetime == None).first()
        return user

    def create_user(self, user_json):
        password = get_random_string(4)
        user = User(
            name=user_json["name"],
            phone=user_json["phone"],
            role=user_json["role"],
            password=generate_password_hash(password=password, method="pbkdf2:sha256", salt_length=int(os.getenv("JWT_SALT_SIZE")))
        )

        db.session.add(user)
        db.session.commit()
        return True, password
    
    def update_user(self, user: User, user_json):
        user_check = self.get_by_phone_without_token(user.phone)
        if user_check :
            db.session.delete(user_check)
        password = get_random_string(4)
        user = User(
            name=user_json["name"],
            phone=user_json["phone"],
            role=user_json["role"],
            password=generate_password_hash(password=password, method="pbkdf2:sha256", salt_length=int(os.getenv("JWT_SALT_SIZE")))
        )
        db.session.add(user)
        db.session.commit()
        return True, password

    def register(self, data):
        user = self.get_by_phone(data["phone"])
        if not user:
            return self.create_user(data)
        else:
            if user.jwt_token == None :
                return False, None
            else :
                if user.registry_exp_datetime < datetime.datetime.utcnow() :
                    return self.update_user(user, data)
                else:
                    return False, user
