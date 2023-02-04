import jwt
from functools import wraps
from flask import request, jsonify
from src.database.models import User
from datetime import datetime, timedelta
from werkzeug.security import generate_password_hash, check_password_hash
from src.utlis.string_utils import get_random_string
from src.service.user_service import UserRepository, UserService
from src.service.jwt_service import JwtRepository, JwtService
from app import db
import os

user_repo = UserRepository()
user_service = UserService(user_repo)

jwt_repo = JwtRepository()
jwt_service = JwtService(jwt_repo)


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
            data = jwt_service.jwt_decode(token)
            user = user_service.get_user_by_phone(data["phone"])
        except:
            return jsonify({"message": "Token is invalid!"}), 401
        return f(user, *args, **kwargs)

    return decorated


def jwt_generator(user: User, password_input: str):
    return jwt_service.generate_token(user, password_input)
