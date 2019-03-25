# Minesweeper
OOP aine projekt - Minesweeper.

Projekti tööülesanded:
1. Luua klass Miiniväli (isendimuutujatega read, veerud, pommide_arv), mis sisaldab maatriksit, kus 'O' täihtsab tühja ruutu ning 'X' tähtistab pommiga ruutu.
   Võiks omada funktsionaalsusi:
    1) Täidab maatriksi ettenähtud pommide arvuga.
    2) Lubab üksikult ruute muuta kas pommideks või tühjadeks ruutudeks.
    3) Prindib miinivälja.
2. Luua static klass Miinimang, mida kasutatakse miinimängu mängimiseks.
   Sisaldab:
   1) Meetodit, mis prindib ekraanile praeguse mänguseisu (mittu ruutu on arvatud, numbrid, käikude arv, kasutatud lippude arv jne).
   2) Meetod, mis võimaldab paigaldada lipu minisse ruutu, märgistamaks võimalikku pommi.
   3) Meetod, mis tuvastab ettenähtud ruudu, olgu see kas tühi, pommi lähedal või pomm ise. Meetod peaks rekursiivselt leidma kõik ruudud, mille lähedal pole ühtki pommi, kui kasutaja ise valib ühe sellise ruudu.
   4) Meetod, mis väärtustab kõik ruudud nende korrektsete pommiarvu viitadega (kui ruudu kaheksas ümbristevas ruudus on 2 pommi, siis ruudu enda väärtuseks saab 2 jne).
   5) Meetod mangi, mis tsüklit kasutades lubab kasutajal mängu mängida.
