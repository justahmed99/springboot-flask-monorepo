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
        token = jwt.encode(
            {
                "name": user.name,
                "phone": user.phone,
                "role": user.role,
                "exp": datetime.utcnow() + timedelta(minutes=30),
            },
            os.getenv("JWT_SECRET_KEY"),
        )

    return status, token


def register(data):
    name, phone, role = data["name"], data["phone"], data["role"]
    password = get_random_string(4)

    user = User.query.filter_by(phone=phone).first()
    if not user:
        user = User(
            name=name,
            phone=phone,
            role=role,
            password=generate_password_hash(
                password=password, method="pbkdf2:sha256", salt_length=16
            ),
        )

        db.session.add(user)
        db.session.commit()
        return True, password
    else:
        return False, None
