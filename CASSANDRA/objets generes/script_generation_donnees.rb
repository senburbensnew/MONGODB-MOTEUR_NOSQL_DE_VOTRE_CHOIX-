require 'json'
require 'securerandom'
require 'date'
require 'faker'

def random_date(start_date, end_date)
  rand(start_date..end_date).to_s
end

def random_text(length)
  ('a'..'z').to_a.sample(length).join
end

def random_sex
  ['Male', 'Female'].sample
end

def random_phone
  "+#{rand(1000000000..9999999999)}"
end

def random_address
  { "numero" => Faker::Address.building_number, "street" => Faker::Address.street_name, "city" => Faker::Address.city, "postal_code" => Faker::Address.zip_code }
end

def random_specialty
  ['Cardiology', 'Neurology', 'Pediatrics', 'Orthopedics', 'Dermatology'].sample
end

def random_motif
  ['Routine Checkup', 'Emergency Visit', 'Follow-up', 'Consultation'].sample
end

medecin_data = 1000.times.map do
  {
    "id" => SecureRandom.uuid,
    "nom" => Faker::Name.last_name,
    "sexe" => random_sex,
    "date_naissance" => random_date(Date.new(1960, 1, 1), Date.new(1995, 12, 31)),
    "specialite" => random_specialty,
    "email" => Faker::Internet.email,
    "cv" => random_text(20),
    "adresse" => random_address,
    "list_telephones" => [random_phone, random_phone],
    "list_prenoms" => [Faker::Name.first_name, Faker::Name.first_name]
  }
end

patient_data = 1000.times.map do
  {
    "id" => SecureRandom.uuid,
    "numero_securite_sociale" => rand(1000000000..9999999999).to_s,
    "nom" => Faker::Name.last_name,
    "sexe" => random_sex,
    "date_naissance" => random_date(Date.new(1960, 1, 1), Date.new(2005, 12, 31)),
    "email" => Faker::Internet.email,
    "poids" => rand(50.0..100.0).round(2),
    "hauteur" => rand(150.0..200.0).round(2),
    "list_telephones" => [random_phone, random_phone],
    "list_prenoms" => [Faker::Name.first_name, Faker::Name.first_name],
    "adresse" => random_address,
    "allergies" => [random_text(5), random_text(6)]
  }
end

File.write('Medecin_By_Speciality.json', JSON.pretty_generate(medecin_data))
File.write('Patient_By_BirthDay.json', JSON.pretty_generate(patient_data))

puts "Files generated successfully!"
