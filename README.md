# graph-world

## Setup the database
`
docker run  --name graph-world -d -p 55437:5432 -e POSTGRES_USER=graphs -e POSTGRES_PASSWORD=verysecurepassword -e POSTGRES_DB=graphs postgres:10-alpine
`
## Connect to the database
`
psql -e -h localhost -p 55437 -U graphs -d graphs
`
## Create database tables

```psql
CREATE TABLE nodes(
	id           SERIAL PRIMARY KEY,
	name 	     TEXT UNIQUE NOT NULL UNIQUE,
	description  TEXT
);

CREATE TABLE edges(
	id          SERIAL PRIMARY KEY,
	source      TEXT REFERENCES nodes(name),
	destination TEXT REFERENCES nodes(name),
	description TEXT,
	unique      (source, destination)
);

CREATE TABLE shortest_paths (
	id             SERIAL  PRIMARY KEY,
	source         TEXT REFERENCES nodes(name),
	destination    TEXT REFERENCES nodes(name),
	shortest_path  TEXT NOT NULL
);
```
## Start web app and create connection pools with database

`
lein repl
`

```clojure
(graph-world.core/reset)
```

## Populate test data in database

The nodes are meant to simulate in a hypothetical situation how many clicks away a person's linkedin profile would be from a simple google search about linkedin
```
"linkedin 		           (Search linkedin on google) -->

 linkedin.com                      (Land on linkedin's homepage) --> 
 
 linkedin.com_search_j             (LinkedIn search for all users who's name starts with 'j') --> 
 
 linkedin.com_profile_jacob_baker" (Linkedin user's profile.. Hurray you reached!!)

Another path being to simulate how many clicks away is a person's linkedin profile  from his college university's alumni page

"mumbaiuniversity                    (Search mumbai university on google)--> 
 
 mumbaiuniversity.com                (Land on mumbai university homepage)--> 
 
 mumbaiuniversity.com_alumni         (Mumbai university alumni page) --> 
 
 mumbaiuniversity.com_alumni_name_j  (Mumbai university alumnus starting with J page)--> 
 
 linkedin.com_profile_jacob_baker"   (Linkedin Profile of alumnus.. Hurray you reached again!)
```

```clojure
;; Creates test nodes
(graph-world.populate-nodes/populate-test-nodes)
;; Creates test edges
(graph-world.populate-edges/populate-edges)
```

## Examples

## Node

Create a node

`
curl  -i  -XPOST -d '{"description": "Clojure guy at obmondo.com"}' -H 'Content-Type: application/json' localhost:11131/node/cavan
`

`
curl  -i  -XPOST -d '{"description": "Fellow Clojurist"}' -H 'Content-Type: application/json' localhost:11131/node/gwenael
`

`
curl  -i  -XPOST -d '{"description": "Someone who i remember vaguely"}' -H 'Content-Type: application/json' localhost:11131/node/mark_randomson
`

Modify node attribute (in this case description)

`
curl  -i  -XPUT -d '{"description": "Nope, just a complete stranger"}' -H 'Content-Type: application/json' localhost:11131/node/mark_randomson
`

Fetch node details
`
curl  -i  -H 'Content-Type: application/json' localhost:11131/node/mark_randomson
`

Delete a node
`
curl  -i -XDELETE  -H 'Content-Type: application/json' localhost:11131/node/mark_randomson
`
## Edges

Create an edge
`
curl  -i  -XPOST -d '{"source": "gwenael", "description": "First session", "destination": "cavan"}' -H 'Content-Type: application/json' localhost:11131/edge
`

Modify edge attribute
`
curl  -i  -XPUT -d '{"source": "gwenael", "description": "Second session", "destination": "cavan"}' -H 'Content-Type: application/json' localhost:11131/edge
`

Delete edge
`
curl  -i  -XDELETE -d '{"source": "gwenael", "destination": "cavan"}' -H 'Content-Type: application/json' localhost:11131/edge
`

...

## Shortest Path
`
graph-world.client=> (calculate-shortest-path "gwenael" "cavan")

"gwenael --> cavan"
`

`
graph-world.client=> (calculate-shortest-path "linkedin" "linkedin.com_profile_jacob_baker")

"linkedin --> linkedin.com --> linkedin.com_search_j --> linkedin.com_profile_jacob_baker"
`

### Would be nice to have

By design i have made it not possible to delete a node if it still has edges to it.
Should probably have a force flag in the delete node request that cascades the edges when node is deleted.

...


## License

Copyright © 2020

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
