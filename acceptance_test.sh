#!/bin/bash

# Définir l'URL du service API
API_URL="http://localhost:8765/sum?a=80&b=20"

# Exécuter la requête cURL pour tester la somme
RESPONSE=$(curl -s "$API_URL")

# Vérifier si la réponse correspond au résultat attendu
EXPECTED_RESULT="100"

if [ "$RESPONSE" -eq "$EXPECTED_RESULT" ]; then
    echo "Test Passed: Expected result $EXPECTED_RESULT received."
    exit 0
else
    echo "Test Failed: Expected result $EXPECTED_RESULT, but got $RESPONSE."
    exit 1
fi

