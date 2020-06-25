
<b>SRB </b>
Igra izvlacenja 6 stapica gde je jedan stapic kraci. Sto broji 6 mesta za igrace. Za stolom stoji krupije koji vodi igru. N igraca periodicno ulazi u prostoriju u intervalima X ( 0<= x <=1 sekunde) i sedaju za sto ako ima mesta. 

Ukoliko je sto pun, igrac izlazi iz prostorije. Nakon sto se sto popuni, partija pocinje. Sto je okrugao i svako mesto je numerisano (od 1 do 6). 

Prva runda pocinje tako sto prvi igrac treba da izvuce stapic iz ruke krupijea. Pre nego sto izvuce stapic, svi ostali igraci pogadjaju jedan od dva ishoda. 

Samo krupije je svestan koji stapic je kraci. Nakon sto svi igraci daju svoju prognozu, prvi igrac izvlaci stapic. U zavisnosti od ishoda, po jedan bod dobija svaki igrac koji je pogodio. 

Ako je igrac izvukao kraci stapic, on napusta sto I partija krece od pocetka. Ako je izvukao normalan stapic onda se prelazi u sledecu rundu u kojoj stapic izvlaci igrac sa sledece pozicije za stolom. Dakle,jedna partija moze da ima 6 rundi.

Odigrava se M rundi pre nego sto krupije ispise ko je pobednik I sa koliko bodova. 

Pravila kodiranja Prave se dve konzolne aplikacije:  

Prva konzolna aplikacija je krupije. Ona ce u sebi imati ServerSocket.  

Druga je konzolna aplikacija ce imati po jednu nit za svakog igraca koja ce otvarati socket konekciju/e. 

Krupije je duzan da:  
Vodi igru  
Da obavestava igrace za koje nema mesta da ne mogu da ucestvuju  
Da ispise rezultat 

Tolerancija na otkaze usled prevelikog broja niti nije obavezna I nece se bodovati. N ce uvek biti “pristojan” broj. 

Svaka poruka mora biti u JSON formatu! 

Igraci nece nasumicno otkazivati te sistem ne mora da bude otporan na otkaze. 

Obe aplikacije ce se izvrsavati na istom racunaru u localhost-u. 

Igrac navodi broj stapica (od 1 do 6) krupijeu. Krupije obavestava tu osobu sta je izvukla a sve ostale igrace obavestava o tome da li su bili u pravu ili ne. 
Krupije obavestava igrace pojedinacno o jednoj od sledecih situacija:  
Da treba da daju prognozu  
Da treba da izvuku stapic 

Svaki igrac treba da poseduje svoj UUID.
