# The database has the following structure as described in the UML diagram below:

+-------------------------+
|         shows           |
+-------------------------+
| id           : SERIAL PK|
| movie_name   : VARCHAR  |
| running_date : timestamp|
| created      : timestamp|
+-------------------------+
           ▲
           |
           | 1
           |
           |--------------------------+
                                      |
                                     *|
+---------------------------------------------+
|        seat_reservations                    |
+---------------------------------------------+
| id         : SERIAL PK                      |
| shows_id   : SERIAL FK → shows(id)          |
| seat       : INT                            |
| created    : timestamp                      |
| UNIQUE     : (shows_id, seat)               |
+---------------------------------------------+




# The classes Shows and SeatReservations have the following UML diagram:

+-----------------------------+
|           Shows            |
+-----------------------------+
| - id : Long                |
| - movieName : String       |
| - runningDate : Date       |
| - created : Date           |
|-----------------------------|
| +getId()                   |
| +getMovieName()            |
| +getRunningDate()          |
| +getCreated()              |
| +getReservations()         |
+-----------------------------+
          1
          |
          |  (mappedBy = "show")
          |
          |----------------------------+
                                       |
                                     * |
                        +-------------------------------+
                        |      SeatReservations          |
                        +-------------------------------+
                        | - id : Long                   |
                        | - seat : Integer              |
                        | - created : Date              |
                        |-------------------------------|
                        | - show : Shows                |
                        | +getShow()                    |
                        | +getSeat()                    |
                        | +getCreated()                 |
                        +-------------------------------+



# REST-API Open-API description:

{
  "openapi": "3.1.0",
  "info": {
    "title": "OpenAPI definition",
    "version": "v0"
  },
  "servers": [
    {
      "url": "http://localhost:8085",
      "description": "Generated server url"
    }
  ],
  "paths": {
    "/createNewReservations": {
      "post": {
        "tags": [
          "cinema-booking-controller"
        ],
        "operationId": "createNewReservations",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/BookingRequest"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/BookingResult"
                }
              }
            }
          }
        }
      }
    },
    "/findAllShows": {
      "get": {
        "tags": [
          "cinema-booking-controller"
        ],
        "operationId": "findAllShows",
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/Shows"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/findAllShowsAndAvailableSeatings": {
      "get": {
        "tags": [
          "cinema-booking-controller"
        ],
        "operationId": "findAllShowsAndAvailableSeatings",
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/BookingResult"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/cancelReservations": {
      "delete": {
        "tags": [
          "cinema-booking-controller"
        ],
        "operationId": "cancelReservation",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/BookingRequest"
              }
            }
          },
          "required": true
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/BookingResult"
                }
              }
            }
          }
        }
      }
    }
  },
  "components": {
    "schemas": {
      "BookingRequest": {
        "type": "object",
        "properties": {
          "showId": {
            "type": "integer",
            "format": "int64"
          },
          "seats": {
            "type": "array",
            "items": {
              "type": "integer",
              "format": "int32"
            }
          }
        }
      },
      "BookingResult": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "movieName": {
            "type": "string"
          },
          "runningDate": {
            "type": "string",
            "format": "date-time"
          },
          "availableSeats": {
            "type": "array",
            "items": {
              "type": "integer",
              "format": "int32"
            }
          }
        }
      },
      "Shows": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "movieName": {
            "type": "string"
          },
          "runningDate": {
            "type": "string",
            "format": "date-time"
          },
          "created": {
            "type": "string",
            "format": "date-time"
          }
        }
      }
    }
  }
}