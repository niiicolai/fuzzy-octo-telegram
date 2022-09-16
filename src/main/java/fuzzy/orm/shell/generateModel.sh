#!/bin/bash

# Get path relative to the location
# of the script.
PATH_CURRENT=$(pwd)
PATH_PARENT_1=$(dirname $PATH_CURRENT)
PATH_PARENT_2=$(dirname $PATH_PARENT_1)
PATH_PARENT_3=$(dirname $PATH_PARENT_2)

# Get all arguments and the model name
MODEL=""
PROPS=()
for (( i=1; i <= "$#"; i++ )); do

  # Find model in first argument
  if [[ $i == 1 ]]; then

    # Split first argument
    IFS='=' read -ra MODEL_PART <<< "${!i}"

    # Ensure the second part is not empty
    if [[ ${MODEL_PART[1]} == "" ]]; then
      echo "ERROR! No model name was provided!"
      exit 64
    fi
    # Insert model name
    if [[ ${MODEL_PART[0]} == "model" ]]; then
      MODEL+=${MODEL_PART[1]^}
    else
      echo "ERROR! The first argument should be the model (Example: model=name)."
      exit 64
    fi
  else
    PROPS+=( ${!i} )
  fi
done

# DEFINE TAB
TAB="\t"
NEWLINE="\n"

# Define props size
# and last prop index
PROPS_SIZE=${#PROPS[@]}
LAST_PROP_INDEX=$(( $PROPS_SIZE - 1))

# Define final directory
# and file type.
MODEL_FILE="$PATH_PARENT_3/models/$MODEL.java"
MIGRATION_FILE="$PATH_PARENT_3/migrations/${MODEL}Migration.java"


echo "========= DIRECTORY INFO ========="
echo "PATH:"
echo $PATH_PARENT_3
echo "MODEL FILE PATH: "
echo $MODEL_FILE
echo "MIGRATION FILE PATH: "
echo $MIGRATION_FILE
echo "========= MODEL INFO ========="
echo "MODEL:"
echo $MODEL
echo "PROPS:"
for i in ${!PROPS[@]}; do
  echo ${PROPS[$i]}
done

# Format arguments to match
# Java constructor argument style
CONSTRUCTOR_ARGUMENTS=""
for i in ${!PROPS[@]}; do
  IFS='=' read -ra PART <<< "${PROPS[$i]}"
  if [[ i -eq ${#PROPS[@]}-1 ]]; then
    CONSTRUCTOR_ARGUMENTS+="${PART[0]} ${PART[1]}"
  else
    CONSTRUCTOR_ARGUMENTS+="${PART[0]} ${PART[1]}, "
  fi
done

# Format arguments to match
# constructor setters without argument
CONSTRUCTOR_DEFAULT=""
for i in ${!PROPS[@]}; do
  IFS='=' read -ra PART <<< "${PROPS[$i]}"

  if [[ ${PART[0]} == "String" ]]; then
    CONSTRUCTOR_DEFAULT+="${TAB}${TAB}set(\"${PART[1]}\", \"\");${NEWLINE}"

  elif [[ ${PART[0]} == "double" ]]; then
    CONSTRUCTOR_DEFAULT+="${TAB}${TAB}set(\"${PART[1]}\", 0.0);${NEWLINE}"

  elif [[ ${PART[0]} == "int" ]]; then
    CONSTRUCTOR_DEFAULT+="${TAB}${TAB}set(\"${PART[1]}\", 0);${NEWLINE}"

  elif [[ ${PART[0]} == "boolean" ]]; then
    CONSTRUCTOR_DEFAULT+="${TAB}${TAB}set(\"${PART[1]}\", false);${NEWLINE}"

  else
    echo "ERROR! Data type: ${PART[0]} is not implemented!"
    exit 64
  fi
done

# Format arguments to match
# constructor setters
CONSTRUCTOR_SETTERS=""
for i in ${!PROPS[@]}; do
  IFS='=' read -ra PART <<< "${PROPS[$i]}"

  if [[ $i == $LAST_PROP_INDEX ]]; then
    CONSTRUCTOR_SETTERS+="${TAB}${TAB}set(\"${PART[1]}\", ${PART[1]});"
  else
    CONSTRUCTOR_SETTERS+="${TAB}${TAB}set(\"${PART[1]}\", ${PART[1]});${NEWLINE}"
  fi
done

# Format arguments to match
# getter methods
GETTER_METHODS=""
for i in ${!PROPS[@]}; do
  IFS='=' read -ra PART <<< "${PROPS[$i]}"
  GETTER_METHODS+="${TAB}public ${PART[0]} get${PART[1]^}()${NEWLINE}${TAB}{${NEWLINE}"
  GETTER_METHODS+="${TAB}${TAB}return (${PART[0]})get(\"${PART[1]}\");${NEWLINE}${TAB}}"
  if [[ $i != $LAST_PROP_INDEX ]]; then
    GETTER_METHODS+=${NEWLINE}${NEWLINE}
  fi
done

# Format arguments to match
# setter methods
SETTER_METHODS=""
for i in ${!PROPS[@]}; do
  IFS='=' read -ra PART <<< "${PROPS[$i]}"
  SETTER_METHODS+="${TAB}public void set${PART[1]^}(${PART[0]} ${PART[1]})${NEWLINE}${TAB}{${NEWLINE}"
  SETTER_METHODS+="${TAB}${TAB}set(\"${PART[1]}\", ${PART[1]});${NEWLINE}${TAB}}"
  if [[ $i != $LAST_PROP_INDEX ]]; then
    SETTER_METHODS+=${NEWLINE}${NEWLINE}
  fi
done

# Format arguments to match
# Migration Add Properties argument style
MIGRATION_ADD_PROPERTIES=""
for i in ${!PROPS[@]}; do
  IFS='=' read -ra PART <<< "${PROPS[$i]}"
  if [[ ${PART[0]} == "String" ]]; then
    MIGRATION_ADD_PROPERTIES+="${TAB}${TAB}properties.put(\"${PART[1]}\", \"\");${NEWLINE}"

  elif [[ ${PART[0]} == "double" ]]; then
    MIGRATION_ADD_PROPERTIES+="${TAB}${TAB}properties.put(\"${PART[1]}\", 0.0);${NEWLINE}"

  elif [[ ${PART[0]} == "int" ]]; then
      MIGRATION_ADD_PROPERTIES+="${TAB}${TAB}properties.put(\"${PART[1]}\", 0);${NEWLINE}"

  elif [[ ${PART[0]} == "boolean" ]]; then
    MIGRATION_ADD_PROPERTIES+="${TAB}${TAB}properties.put(\"${PART[1]}\", false);${NEWLINE}"

  else
    echo "ERROR! Data type: ${PART[0]} is not implemented!"
    exit 64
  fi
done

# copy files
cp modelTemplate.txt $MODEL_FILE
cp migrationTemplate.txt $MIGRATION_FILE

# Insert Model code
sed -i '' -e "s/#ClassName/$MODEL/g" $MODEL_FILE
sed -i '' -e "s/#ConstructorArguments/$CONSTRUCTOR_ARGUMENTS/g" $MODEL_FILE
sed -i '' -e "s/#ConstructorSetters/$CONSTRUCTOR_SETTERS/g" $MODEL_FILE
sed -i '' -e "s/#ConstructorDefault/$CONSTRUCTOR_DEFAULT/g" $MODEL_FILE
sed -i '' -e "s/#GetterMethods/$GETTER_METHODS/g" $MODEL_FILE
sed -i '' -e "s/#SetterMethods/$SETTER_METHODS/g" $MODEL_FILE

# Insert Migration code
sed -i '' -e "s/#ClassName/$MODEL/g" $MIGRATION_FILE
sed -i '' -e "s/#AddProperties/$MIGRATION_ADD_PROPERTIES/g" $MIGRATION_FILE
