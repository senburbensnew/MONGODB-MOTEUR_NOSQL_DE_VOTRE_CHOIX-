require 'faker'
require 'json'
require 'date'
require 'mongo'

def generate_random_document
  {
    "_id" => BSON::ObjectId.new,
    "nom" => Faker::Name.last_name,
    "sexe" => ["Male", "Female", "Other"].sample,
    "date_naissance" => Faker::Date.birthday(min_age: 25, max_age: 65),
    "specialite" => ["Cardiologist", "Dermatologist", "Pediatrician", "Orthopedic Surgeon", "Neurologist", "Gynecologist"].sample,
    "email" => Faker::Internet.email,
    "cv" => Faker::Lorem.sentence(word_count: 10),
    "list_telephones" => [Faker::PhoneNumber.phone_number, Faker::PhoneNumber.phone_number],
    "list_prenoms" => [Faker::Name.first_name, Faker::Name.first_name],
    "adresse" => {
      "numero" => Faker::Address.building_number,
      "rue" => Faker::Address.street_name,
      "code_postal" => Faker::Address.zip_code,
      "ville" => Faker::Address.city
    },
    "list_rendez_vous" => [
      {
        "patient" => BSON::ObjectId.new,
        "date_rendez_vous" => Faker::Date.forward(days: 23),
        "motif" => Faker::Lorem.sentence(word_count: 3)
      }
    ],
    "list_consultations" => [
      {
        "patient" => BSON::ObjectId.new,
        "raison" => Faker::Lorem.sentence(word_count: 3),
        "diagnostic" => Faker::Lorem.sentence(word_count: 5),
        "date_consultation" => Faker::Date.backward(days: 30),
        "list_examens" => [
          {
            "details_examen" => Faker::Lorem.sentence(word_count: 5),
            "date_examen" => Faker::Date.backward(days: 15)
          }
        ],
        "list_prescriptions" => [
          {
            "details_prescription" => Faker::Lorem.sentence(word_count: 5),
            "date_prescription" => Faker::Date.backward(days: 10)
          }
        ]
      }
    ]
  }
end

documents = Array.new(1000) { generate_random_document }

File.open("medecins_data.json", "w") do |f|
  f.write(JSON.pretty_generate(documents))
end
