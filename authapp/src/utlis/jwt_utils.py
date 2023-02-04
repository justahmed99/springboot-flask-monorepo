import jwt
from functools import wraps
from flask import request, jsonify
from src.database.models import User
from datetime import datetime, timedelta
from werkzeug.security import generate_password_hash, check_password_hash
from src.utlis.string_utils import get_random_string
from app import db
import os


def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = None
        if "Authorization" in request.headers:
            print("authorization exist")
            token = request.headers["Authorization"].replace("Bearer ", "")
        
        if not token:
            return jsonify({"message": "Token is missing!"}), 401

        try:
            # payload decode
            data = jwt.decode(token, os.getenv("JWT_SECRET_KEY"), algorithms=["HS256"])
            user = User.query.filter_by(phone=data["phone"]).first()
        except:
            return jsonify({"message": "Token is invalid!"}), 401
        return f(user, *args, **kwargs)

    return decorated


def jwt_generator(user: User, password_input: str):
    status = False
    token = None
    if check_password_hash(user.password, password_input):
        status = True
        # generate JWT Token
        if user.jwt_token == None :
            current_date = datetime.utcnow()
            user.registry_iat_datetime = current_date
            user.registry_exp_datetime = user.registry_iat_datetime + timedelta(minutes=int(os.getenv("JWT_VALIDITY_TIME")))
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


def register(data):
    name, phone, role = data["name"], data["phone"], data["role"]
    password = get_random_string(4)
    current_date = datetime.utcnow()

    user = User.query.filter_by(phone=phone).first()
    if not user:
        user = User(
            name=name,
            phone=phone,
            role=role,
            password=generate_password_hash(
                password=password, method="pbkdf2:sha256", salt_length=16
            )
        )

        db.session.add(user)
        db.session.commit()
        return True, password
    else:
        if user.jwt_token == None :
            return False, None
        else :
            print(user.registry_exp_datetime)
            if user.registry_exp_datetime < datetime.utcnow() :
               user = User(
                name=name,
                phone=phone,
                role=role,
                password=generate_password_hash(
                    password=password, method="pbkdf2:sha256", salt_length=16
                )
            )
            db.session.add(user)
            db.session.commit()
            return True, password
