# flask imports
from flask import Flask, request, jsonify, make_response
from werkzeug.security import generate_password_hash
from flask_migrate import Migrate
from src.database.models import db, User
from src.utlis.jwt_utils import token_required, jwt_generator, register
from src.utlis.string_utils import get_random_string
import os

from dotenv import load_dotenv

load_dotenv()

# creates Flask object
app = Flask(__name__)
app.config.update(
    DEBUG=os.getenv("DEBUG") == True,
    SQLALCHEMY_DATABASE_URI=os.getenv("SQLALCHEMY_DATABASE_URI"),
    SQLALCHEMY_TRACK_MODIFICATIONS=os.getenv("SQLALCHEMY_TRACK_MODIFICATIONS"),
    JWT_SECRET_KEY=os.getenv("JWT_SECRET_KEY"),
)
# app.config.from_object(os.environ["APP_SETTINGS"])
db.init_app(app)
migrate = Migrate(app, db, include_schemas=True)


# get user detail route
@app.route("/auth/user", methods=["GET"])
@token_required
def get_user_detail(current_user):
    return jsonify(
        {
            "user": {
                "name": current_user.name,
                "phone": current_user.phone,
                "role": current_user.role,
            }
        }
    )


# login route
@app.route("/auth/login", methods=["POST"])
def login():
    # creates dictionary of form data
    auth = request.get_json()

    if not auth or not auth["phone"] or not auth["password"]:
        # returns 401 if any email or / and password is missing
        return make_response(
            jsonify({"message": "phone number and password is required!"}), 400
        )

    user = db.session.query(User).filter(User.phone == auth["phone"]).first()

    if not user:
        # returns 403 if user does not exist
        return make_response(
            jsonify({"message": "incorrect phone number or password!"}), 403
        )

    status, token = jwt_generator(user, auth["password"])
    if status:
        return make_response(jsonify({"token": token.encode().decode("utf-8")}), 200)

    # returns 403 if password is wrong
    return make_response(
        jsonify({"message": "incorrect phone number or password!"}), 403
    )


# register route
@app.route("/auth/register", methods=["POST"])
def signup():
    data = request.get_json()

    status, password = register(data)

    if status:
        return make_response(jsonify({"password": password}), 201)
    else:
        return make_response(
            jsonify({"message": "User already exists. Please Log in."}), 202
        )


if __name__ == "__main__":
    app.run()
