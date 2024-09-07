// Conversion du MCD MERISE en des objets MONGODB et du
// moteur nosql de votre choix et classes java

// Spécification des modèles de documents à mettre dans chaque collection
 
// Collection "Medecin"
{
    "_id": "ObjectId",
	"numero_securite_sociale" : "String",
    "nom": "String",
	"sexe" : "String",
	"date_naissance" : "Date",
    "specialite": "String",
    "email": "String",
	"cv" : "String",
    "adresse": {
	  "numero": "Number"
      "rue": "String",
      "code_postal": "Number",
	  "ville": "String"
    },
	"list_telephones": ["String"],
	"list_prenoms" : ["String"],
	"list_rendez_vous" : [
		{	
			"patient": "ObjectId",  // Référence à Patient
			"date_rendez_vous": "Date",
			"motif": "String",
		}
	]
	"list_consultations" : [
		{
			"patient": "ObjectId",  // Référence à Patient
			"raison" : "String",
			"diagnostic" : "String",
			"date_consultation" : "Date",
			"list_examens" : [
				{
					"details_examen" : "String",
					"date_examen" : "Date"
				}
			],
			"list_prescriptions" : [
				{
					"details_prescription" : "String",
					"date_prescription" : "Date"
				}
			]
		}
	]
 }

// Collection "Patient"
{
	"_id": "ObjectId",
	"numero_securite_sociale" : "String",
    "nom": "String",
	"sexe" : "String",
	"date_naissance" : "Date",
    "email": "String",
	"poids" : "Number",
	"hauteur" : "Number",
	"list_telephones": ["String"],
	"list_prenoms" : ["String"],
    "adresse": {
	  "numero": "Number"
      "rue": "String",
      "code_postal": "Number",
	  "ville": "String"
    },
	"list_rendez_vous" : [
		{	
			"medecin": "ObjectId",  // Référence au medecin
			"date_rendez_vous": "Date",
			"motif": "String",
		}
	],
	"list_consultations" : [
		{
			"medecin": "ObjectId",  // Référence au medecin
			"raison" : "String",
			"diagnostic" : "String",
			"date_consultation" : "Date",
			"list_examens" : [
				{
					"details_examen" : "String",
					"date_examen" : "Date"
				}
			],
			"list_prescriptions" : [
				{
					"details_prescription" : "String",
					"date_prescription" : "Date"
				}
			],
			"facture" : {
				"Montant_Total" : "Number",
				"date_facture" : "Date"
			}
		}
	],
    "antecedentsMedicaux": [
      {
        "type": "String",
        "description": "String",
        "date": "Date"
      }
    ],
    "allergies": ["String"]
}
