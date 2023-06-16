#!/bin/sh

# shellcheck disable=SC2046  disable=SC2006
cd "../../../"
outPath="./out/production/classes"
githubPath="${HOME}/Documents/GitHub"
modules="audio calculate color file gui math poison time"
rm -fr "${githubPath}/.otl/analyzer/"*

#  (work bin Main) file copy
# shellcheck disable=SC2066
for file in "${outPath}/"*".class" ; do
  cp -afR "${file}" "${githubPath}/.otl/analyzer"
done

cp -afR "${outPath}/bin" "${githubPath}/.otl/analyzer"
cp -afR "${outPath}/work" "${githubPath}/.otl/analyzer"
cp -afR "./shell/"* "${githubPath}/.otl"

path=`pwd`
# shellcheck disable=SC2164
cd "${githubPath}/.otl/analyzer"
zip -r "${githubPath}/otl-all-module/download-tool/analyzer.zip" ./*
# shellcheck disable=SC2164
cd "${path}"

# module setting
modulePath="${githubPath}/module"
# shellcheck disable=SC2154 disable=SC2039 disable=SC2044
for module in $modules ; do
  mkdir -p "${modulePath}/${module}"
  rm -fr "${modulePath}/${module}/"*".class"
  touch "${modulePath}/${module}/system.otls"
  echo "class:" > "${modulePath}/${module}/system.otls"
  for files in "${outPath}/cos/${module}/"*".class" ; do
    for file in `find "${files}"` ; do
      echo "    $(echo "${file:29}" | sed "s/\//~/g")" >> "${modulePath}/${module}/system.otls"
      cp -a "${file}" "${modulePath}/${module}/$(basename "${file}")"
    done
  done
done

# jar setting
for files in "./file/jar/"* ; do
  if [ -d "${files}" ]; then
    typePath="${modulePath}/$(basename "${files}")"
    if [ -f "${typePath}/system.otls" ]; then
      echo "jar:" >> "${typePath}/system.otls"
      for file in "${files}/"*".jar" ; do
        echo "    $(basename "${file}")" >> "${typePath}/system.otls"
        cp -a "${file}" "${typePath}/$(basename "${file}")"
      done
    fi
  fi
done

# other setting
for files in "./file/other/"* ; do
  if [ -d "${files}" ]; then
    typePath="${modulePath}/$(basename "${files}")"
    if [ -f "${typePath}/system.otls" ]; then
      echo "other:" >> "${typePath}/system.otls"
      for file in "${files}/"* ; do
        cp -a "${file}" "${typePath}/$(basename "${file}")"
      done
    fi
  fi
done

# add setting
for files in "./file/add/"* ; do
  if [ -d "${files}" ]; then
    typePath="${modulePath}/$(basename "${files}")"
    touch "${typePath}/add.otls"
    extension=""
    for file in "${files}/"* ; do
      cp -a "${file}" "${typePath}/$(basename "${file}")"
      extension=".${file##*.}"
    done
    echo "${extension}" > "${typePath}/add.otls"
  fi
done