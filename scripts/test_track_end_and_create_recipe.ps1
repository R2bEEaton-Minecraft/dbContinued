$ErrorActionPreference = 'Stop'

$recipePath = Join-Path $PSScriptRoot '..\src\main\resources\data\davebuildingmod\recipes\steel_block.json'
$modelPath = Join-Path $PSScriptRoot '..\src\main\resources\assets\davebuildingmod\models\custom\create_track_end.json'

if (-not (Test-Path -LiteralPath $recipePath)) {
    throw 'Expected the Create steel-block mixing recipe to be packaged with the mod.'
}

$recipe = Get-Content -Raw -LiteralPath $recipePath | ConvertFrom-Json
if ($recipe.type -ne 'create:mixing' -or $recipe.heatRequirement -ne 'heated' -or $recipe.results.Count -ne 1 -or $recipe.results[0].item -ne 'davebuildingmod:steel_block' -or $recipe.results[0].count -ne 1) {
    throw 'The Create steel-block mixing recipe does not produce one steel block with heat.'
}

$model = Get-Content -Raw -LiteralPath $modelPath | ConvertFrom-Json
$bumperFaces = @($model.elements[14].faces.north, $model.elements[15].faces.north)
if (($bumperFaces | Where-Object { $_.texture -ne '#missing' }).Count -ne 0) {
    throw 'The Buffer Track bumper plates must retain their polished-andesite texture mapping.'
}
if ($model.elements[0].faces.north.texture -ne '#1' -or $model.elements[0].faces.south.texture -ne '#1') {
    throw 'The Buffer Track rail faces must retain the standard-track texture mapping.'
}

$blockModelPath = Join-Path $PSScriptRoot '..\src\main\resources\assets\davebuildingmod\models\block\track_end.json'
$blockModel = Get-Content -Raw -LiteralPath $blockModelPath | ConvertFrom-Json
if ($blockModel.render_type -ne 'cutout_mipped') {
    throw 'The Buffer Track must use alpha-tested mipmapped rendering so its track surfaces do not blend through each other.'
}
